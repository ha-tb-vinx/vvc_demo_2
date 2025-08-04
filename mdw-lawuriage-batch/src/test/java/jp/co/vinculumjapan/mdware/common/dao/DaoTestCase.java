package jp.co.vinculumjapan.mdware.common.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import jp.co.vinculumjapan.swc.commons.dao.DaoIf;
import jp.co.vinculumjapan.swc.commons.dao.DaoInvokerIf;
import jp.co.vinculumjapan.swc.commons.dao.StandAloneDaoInvoker;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * DBUnitテストクラスのベースクラス.<br>
 * <dl>
 *   <dt>利用方法</dt>
 *   <dd>
 *     <ol>
 *       <li>サブクラスのコンストラクタで、DAOクラスが使用するデータソース設定名を引数に、DaoTestCaseクラスのコンストラクタを呼びます。<br><br></li>
 *       <li>原則的に、DatabaseTestCaseのsetUp()にて、「DaoTestCase.properties」に指定されたデータソースに対し、「（テストクラス名）.xls」と命名されたエクセルファイルを、エクセルファイルのシート名に対応したテーブルにdelete-insertします。<br><br></li>
 *       <li>複数のスキーマにテストデータを配置したい場合などは、例外的に、各々のスキーマ用のテストデータ用エクセルファイルを作成し、サブクラスのコンストラクタで[xlsInfos]というプロパティに文字列配列を設定します。
 *         <pre>
 * String[] daily = new String[] {
 *         "MDW_DAILYORDER",
 *         "MDW_DAILYORDER",
 *         "MDW_DAILYORDER",
 *         "src/test/resources/jp/co/vinculumjapan/mdware/dailyorder/dao/web/impl/DailyOrderWeeklyRetrieveDaoTestDaily.xls" };
 * String[] jiseki = new String[] {
 *         "MDW_JISEKI",
 *         "MDW_JISEKI",
 *         "MDW_JISEKI",
 *         "src/test/resources/jp/co/vinculumjapan/mdware/dailyorder/dao/web/impl/DailyOrderWeeklyRetrieveDaoTestJiseki.xls" };
 * this.xlsInfos = new Object[] { daily, jiseki };
 *         </pre>
 *       </li>
 *       <li>テストメソッドにて、必要なInputインスタンスを作成後、[acquireOutput]メソッドを呼ぶとObject型でOutputインスタンスを返します。<br><br></li>
 *       <li>実行結果取得後、[collate]メソッドにてテストログ出力と同値検証を行います。</li>
 *     </ol>
 *   </dd>
 * </dl>
 * 
 * @author 09204016238
 */
public abstract class DaoTestCase extends DatabaseTestCase {

    /***************************************************************************
     * 
     * 定数
     * 
     **************************************************************************/

    /** プロパティファイル名. */
    private static final String PROPERTY_KEY = "jp.co.vinculumjapan.mdware.common.dao.DaoTestCase";

    /** DB接続ドライバ名のプロパティキー. */
    private static final String DRIVER_KEY = "driver";

    /** DB接続URLのプロパティキー. */
    private static final String URL_KEY = "url";

    /** DB接続ユーザのプロパティキー. */
    private static final String SCHEMA_KEY = "schema";

    /** DB接続ユーザのプロパティキー. */
    private static final String USR_KEY = "usr";

    /** DB接続パスワードのプロパティキー. */
    private static final String PWD_KEY = "pwd";

    /** log4jプロパティファイル名. */
    private static final String LOG_PROPERTY_KEY = "src/test/resources/jp/co/vinculumjapan/mdware/common/dao/DaoTestCaseLog4j.properties";

    /** テスト完了時のメッセージ. */
    protected static final String MSG_OK = "**********　ユニットテスト　正常終了　**********";

    /***************************************************************************
     * 
     * インスタンス変数
     * 
     **************************************************************************/

    /** DB接続ドライバ. */
    private String driver;

    /** DB接続URL. */
    private String url;

    /** DB接続スキーマ. */
    private String schema;

    /** DB接続ユーザ. */
    private String usr;

    /** DB接続パスワード. */
    private String pwd;

    /** DAOの実行クラス. */
    private DaoInvokerIf invoker;

    /** DAOの実行ユーザ. */
    private String invokerUserId;

    /** 検査対象DAOクラス名. */
    private String daoName;

    /** 検査対象DAOクラス名. */
    private boolean isSetUpTestData;

    /** デフォルトのテストデータエクセルファイルのプロジェクトルートからのパス. */
    private String xls;

    /** 複数テストデータエクセルファイル毎の[1:スキーマ][2:ユーザ][3:パスワード][4:プロジェクトルートからのパス]のリストの配列. */
    protected Object[] xlsInfos;

    /** ユニットテスト結果ログ用ロガー. */
    protected Logger logger;

    /***************************************************************************
     * 
     * コンストラクタ.<br>
     * 各プロパティの初期値を「DaoTestCase.properties」より設定。
     * 
     * @param dbNm テスト対象のDAOクラスが使用するDB設定名称。内部保持のinvokerに渡す。
     * @param isSetUpTestData setUp()メソッドで、テストデータをDBに登録するか。
     * @param invokerUserId DBに登録するユーザーＩＤ
     * 
     **************************************************************************/

    protected DaoTestCase(String dbNm, boolean isSetUpTestData, String invokerUserId) {
        super();

        // ロガーの初期化
        this.logger = Logger.getLogger(this.getClass().getName());
        PropertyConfigurator.configure(DaoTestCase.LOG_PROPERTY_KEY);

        // testクラスのプロパティの初期化
        ResourceBundle rb = ResourceBundle.getBundle(DaoTestCase.PROPERTY_KEY);
        this.driver = rb.getString(DaoTestCase.DRIVER_KEY);
        this.url = rb.getString(DaoTestCase.URL_KEY);
        this.schema = rb.getString(DaoTestCase.SCHEMA_KEY);
        this.usr = rb.getString(DaoTestCase.USR_KEY);
        this.pwd = rb.getString(DaoTestCase.PWD_KEY);

        this.invokerUserId = invokerUserId;
        this.invoker = new StandAloneDaoInvoker(dbNm, this.invokerUserId);

        try {

            /**
             * テスト対象DAOクラスが属性宣言部（コンストラクタより前）で初期化している場合、
             * ResorceUtilなど内部でLoggerを初期化するオブジェクトを使用して初期化していると、
             * 初期化用のコンテナが存在しない（プロパティファイルのディレクトリがわからない）のでコケる。
             * ここ（テストの最初）でLogger用のコンテナを初期化しておく。
             */

            this.invoker.initSession(null);
            this.invoker.endSession(null);
            //            String location = System.getProperty("user.dir").replace('\\', '/');
            //            List ar = DaoUtils.readFile(location + "/propertie.location");
            //            String prDir = ((String) ar.get(0)).trim();
            //            StcLog.init(prDir + "/stcliblog4j.properties");
            //            CollectConnections cl = CollectConnections.getInstance();
            //            cl.setUsePool(false);
            //            cl.init(prDir + "/database.properties");

        } catch (SQLException e) {
            fail("invokerのセッションの初期化に失敗しました。");
            e.printStackTrace();
        } catch (IOException e) {
            fail("propertie.locationがありません。");
            e.printStackTrace();
        }

        this.daoName = this.getClass().getName().replaceFirst("Test$", "");

        this.isSetUpTestData = isSetUpTestData;
        this.xls = "src/test/resources/" + this.getClass().getName().replace('.', '/') + ".xls";
    }

    protected DaoTestCase(String dbNm) {
        this(dbNm, true, "");
    }

    protected DaoTestCase(String dbNm, String invokerUserId) {
        this(dbNm, true, invokerUserId);
    }

    /***************************************************************************
     * 
     * プロテクトメソッド<br>
     * 
     **************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getConnection()
     */
    protected final IDatabaseConnection getConnection() throws Exception {
        return this.getConnection(this.schema, this.usr, this.pwd);
    }

    /**
     * スキーマ・ユーザ・パスワードを指定してコネクションを取得
     * 
     * @param _schema 接続スキーマ
     * @param _usr 接続ユーザID
     * @param _pwd 接続パスワード
     * @return
     * @throws Exception
     */
    protected final IDatabaseConnection getConnection(String _schema, String _usr, String _pwd) throws Exception {
        Class.forName(this.driver);
        Connection con = DriverManager.getConnection(this.url, _usr, _pwd);
        return new DatabaseConnection(con, _schema);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getDataSet()
     */
    protected final IDataSet getDataSet() throws Exception {
        if (this.isSetUpTestData && (this.xlsInfos == null || this.xlsInfos.length == 0)) {
            return this.getDataSet(this.xls);
        } else {
            return null;
        }
    }

    /**
     * エクセルファイルのソースルートからのパスを指定してデータセットを取得
     * 
     * @param _excel データセットを取得するエクセルファイル
     * @return
     * @throws Exception
     */
    protected final IDataSet getDataSet(String _excel) throws Exception {
        FileInputStream fis = null;
        XlsDataSet xds = null;
        try {
            fis = new FileInputStream(_excel);
            xds = new XlsDataSet(fis);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (DataSetException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
            }
        }
        return xds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#setUp()
     */
    protected final void setUp() throws Exception {
        super.setUp();

        if (this.xlsInfos != null && this.xlsInfos.length > 0) {
            IDatabaseConnection cn = null;
            try {
                for (int i = 0; i < this.xlsInfos.length; i++) {
                    String[] xlsInfo = (String[]) this.xlsInfos[i];
                    cn = this.getConnection(xlsInfo[0], xlsInfo[1], xlsInfo[2]);
                    IDataSet ds = this.getDataSet(xlsInfo[3]);
                    DatabaseOperation.CLEAN_INSERT.execute(cn, ds);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    if (cn != null) {
                        cn.close();
                    }
                } catch (SQLException e) {
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dbunit.DatabaseTestCase#getSetUpOperation()
     */
    protected final DatabaseOperation getSetUpOperation() throws Exception {

        if (!this.isSetUpTestData) {
            // テストデータを使用しない場合は、
            // デフォルトのテストデータエクセルファイルをDBに登録しない
            return DatabaseOperation.NONE;
        } else if (this.xlsInfos != null && this.xlsInfos.length > 0) {
            // テストデータエクセルファイルを指定している場合は、
            // デフォルトのテストデータエクセルファイルをDBに登録しない
            return DatabaseOperation.NONE;
        } else {
            return DatabaseOperation.CLEAN_INSERT;
        }
    }

    /**
     * テストデータ用のデフォルトエクセルファイルを取得する.<br>
     * 
     * @return テストデータ用のデフォルトエクセルファイルのデータセット
     * @throws Exception 
     */
    protected final IDataSet getDefaultTestDataSet() throws Exception {
        IDataSet ds = null;
        if (this.xlsInfos == null || this.xlsInfos.length == 0) {
            ds = this.getDataSet(this.xls);
        }
        return ds;
    }

    /**
     * 期待値用エクセルファイルを取得する.<br>
     * テストクラス名を基に、「src/test/resources/ +（テストクラスのフルネーム）+ Expected.xls」を補填して、期待値データ用エクセルファイルのデータセットを返します。
     * 
     * @return 期待値用エクセルファイルのデータセット
     * @throws Exception 
     */
    protected final IDataSet getExpectedDataSet() throws Exception {
        String _excel = "src/test/resources/" + this.getClass().getName().replace('.', '/') + "Expected.xls";
        IDataSet ds = this.getDataSet(_excel);
        return ds;
    }

    /**
     * 期待値用エクセルシートを取得する.<br>
     * テストクラス名を基に取得したデータセットから「EXPECTED」という名称のシートのテーブルを返します。
     * 
     * @return 期待値用エクセルシートのテーブル
     */
    protected final ITable getExpectedTable() throws Exception {
        IDataSet ds = this.getExpectedDataSet();
        ITable t = ds.getTable("EXPECTED");
        return t;
    }

    /**
     * DAOクラスを実行し、outputオブジェクトを返します.<br>
     * 
     * @param input 各テスト対象DAOクラスのInputインスタンス
     * @return 各テスト対象DAOクラスのOutputインスタンス
     */
    protected final Object acquireOutput(Object input) throws Exception {

        if (input != null) {
            this.logger.debug("[ 実行パラメータ ]");
            this.printTrace(input, 1);
        }
        this.logger.debug("[ 実行結果 ]");

        DaoIf dao = (DaoIf) Class.forName(this.daoName).newInstance();
        dao.setInputObject(input);
        this.invoker.InvokeDao(dao);
        Object output = dao.getOutputObject();
        return output;
    }

    /**
     * 任意の更新クエリを実行.<br>
     * （主にSysCntlMtのオンライン日付などの軽微な更新を想定）
     * 
     * @param sql
     * @return
     * @throws Exception
     */
    protected final int executeUpdate(String sql) {
        int ret = 0;
        Connection cn = null;
        Statement stmt = null;
        try {
            Class.forName(this.driver);
            cn = DriverManager.getConnection(this.url, this.usr, this.pwd);
            stmt = cn.createStatement();
            ret = stmt.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            fail("ドライバクラスが正しくありません。");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("クエリの実行に失敗しました。:" + sql);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                }
            }
        }
        return ret;
    }

    /**
     * 任意のテーブルをbeanのListにコピーする
     * 
     * @param table コピー元のテーブル
     * @param inputClass コピー先のRowBeanの型
     * @return
     * @throws Exception
     */
    protected final List table2Beans(ITable table, Class inputClass) throws Exception {

        // 戻り値用リスト
        List beans = new ArrayList();

        // テーブル列のメタデータ
        Column[] cols = table.getTableMetaData().getColumns();

        // テーブル行でループ
        for (int i = 0; i < table.getRowCount(); i++) {

            // リスト要素の新規インスタンスを生成
            Object bean = inputClass.newInstance();

            // テーブル列でループ
            for (int j = 0; j < cols.length; j++) {

                // テーブル列名とセル内容を取得
                String colName = cols[j].getColumnName();
                Object value = table.getValue(i, colName);

                // リスト要素の属性に値を設定
                Field field = bean.getClass().getDeclaredField(colName);
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (Exception e) {
                    if (field.getType() == BigDecimal.class) {
                        field.set(bean, new BigDecimal((String) value));
                    } else if (field.getType() == String.class) {
                        field.set(bean, ((BigDecimal) value).toString());
                    }
                }
            }
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 実行時例外を取得して期待値と比較する
     * 
     * @param input DAO実行用のInputオブジェクト
     * @param expected inputを使用した際にDAOから投げられた例外オブジェクト
     * @throws Exception
     */
    protected final void expectException(Object input, Exception expected) throws Exception {
        try {
            this.acquireOutput(input);
            fail("例外が何も投げられませんでした。");
        } catch (Exception actual) {
            this.collate("実行時例外の同値検証", expected, actual);
        }
    }

    /**
     * 指定したファイル・フォルダを削除.
     * @param path
     */
    protected final void delete(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    delete(file.getAbsolutePath());
                } else {
                    files[i].delete();
                }
            }
        }
        dir.delete();
    }

    /**
     * 指定ディレクトリ直下のファイルのみ削除.
     * @param dataDirPath
     */
    protected final void deleteFiles(String dataDirPath) {
        File dir = new File(dataDirPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 指定したファイルをコピー.
     * @param fromAbsolutePath
     * @param toAbsolutePath
     * @return
     */
    protected final int copy(String fromAbsolutePath, String toAbsolutePath) {

        int returnCode = 0;

        ArrayList copyCommand = new ArrayList();
        copyCommand.add("cmd");
        copyCommand.add("/C");
        copyCommand.add("copy");
        copyCommand.add("/B");
        copyCommand.add("/Y");
        copyCommand.add(fromAbsolutePath);
        copyCommand.add(toAbsolutePath);
        String[] command = (String[]) copyCommand.toArray(new String[copyCommand.size()]);

        try {
            Process p = Runtime.getRuntime().exec(command);
            returnCode = p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnCode;
    }

    /**
     * 指定した文字列を指定桁でRPAD.<br>
     * （ログ出力用）
     * 
     * @param s
     * @param length
     * @return
     */
    private String pad(String s, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = s.getBytes().length; i < length; i++) {
            sb.append(" ");
        }
        return s + sb.toString();
    }

    /**
     * ユニットテストの実行ログを出力
     * 
     * @param name テスト項目名
     * @param expected 期待値
     * @param expectedType 期待値のデータ型
     * @param actual 実行値
     * @param actualType 実行値のデータ型
     */
    private void printDebug(String name, Object expected, String expectedType, Object actual, String actualType) {
        this.logger.debug("    ・[名称]-<期待値>:<実行結果> = " + this.pad("[" + name + "]", 30) + " - " + this.pad("<" + expected + ">(" + expectedType.replaceAll("class\\s", "") + ")", 50) + " : "
                + this.pad("<" + actual + ">(" + actualType.replaceAll("class\\s", "") + ")", 50));
    }

    /**
     * オブジェクトの内容をユニットテスト用ログにトレース
     * 
     * @param input トレースするオブジェクト
     * @param indentQt ログ出力時のインデント数
     * @throws Exception
     */
    private void printTrace(Object input, int indentQt) throws Exception {

        // インデント数に応じてインデント量を決定
        String indentTx = "    ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < indentQt; i++) {
            sb.append(indentTx);
        }
        String indent = sb.toString();

        this.logger.debug(indent + "・" + input.toString());
        Field[] fields = input.getClass().getDeclaredFields();

        //もひとつインデント
        ++indentQt;
        indent += indentTx;

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.getType() == List.class) {
                this.logger.debug(indent + "・" + field.getName());
                List list = (List) field.get(input);
                ++indentQt;
                for (Iterator iList = list.iterator(); iList.hasNext();) {
                    Object object = (Object) iList.next();
                    this.printTrace(object, indentQt);
                }
            } else {
                this.logger.debug(indent + "・" + this.pad(field.getName(), 20) + " = " + field.get(input));
            }
        }
    }

    /***************************************************************************
     * 
     * 検証メソッド
     * 
     **************************************************************************/

    /**
     * 整数値のテストログ出力と同値検証
     * 
     * @param name テスト項目の名称
     * @param expected 期待値
     * @param actual 実行値
     */
    protected final void collate(String name, int expected, int actual) throws Exception {
        this.printDebug(name, String.valueOf(expected), "int", String.valueOf(actual), "int");
        assertEquals(name, expected, actual);
    }

    /**
     * 真偽値のテストログ出力と同値検証.
     *
     * @param name テスト項目の名称
     * @param expected 期待値
     * @param actual 実行値
     * @throws Exception Exception
     */
    protected final void collate(String name, boolean expected, boolean actual) throws Exception {
        this.printDebug(name, String.valueOf(expected), "boolean", String.valueOf(actual), "boolean");
        assertEquals(name, expected, actual);
    }

    /**
     * 参照型のテストログ出力と同値検証.<br>
     * <ol>
     * <li>期待値データ用エクセルファイルに「IGNORE」と記述したセルについては、同値検証を行いません。
     * <li>期待値データ用エクセルファイルに「NOT NULL」と記述したセルについては、NULLでないかを確認します。
     * <li>期待値データ用エクセルファイルに「NOT EQUAL」と記述したセルについては、同値でないかを確認します。
     * <li>小数値項目については、期待値の小数桁を実行値の小数桁に合わせます。
     * 
     * @param name テスト項目の名称
     * @param expected 期待値
     * @param actual 実行値
     */
    protected final void collate(String name, Object expected, Object actual) throws Exception {

        Object expectedValue4Log = expected;
        String expectedType4Log = "NULL";
        Object actualValue4Log = actual;
        String actualType4Log = "NULL";

        if (actual instanceof String) {
            // 実行値が文字列の場合

            // 実行値のログ出力値を編集
            actualValue4Log = (String) actual;

            // 期待値を実行値に型合わせ
            if (expected == null) {
                expected = "";
            } else if (expected instanceof BigDecimal) {
                expected = ((BigDecimal) expected).toString();
            }

            // 期待値のログ出力値を編集
            expectedValue4Log = (String) expected;

            // 期待値・実行値のデータ型を編集
            expectedType4Log = expected == null ? "NULL" : expected.getClass().toString();
            actualType4Log = actual == null ? "NULL" : actual.getClass().toString();

        } else if (actual instanceof BigDecimal) {
            // 実行値が数値の場合

            // 実行値のログ出力値を編集
            actualValue4Log = ((BigDecimal) actual).toString();

            // 期待値を実行値に桁合わせ
            int actualScale = ((BigDecimal) actual).scale();
            if (expected instanceof String) {
                expected = new BigDecimal((String) expected);
                expected = ((BigDecimal) expected).setScale(actualScale, BigDecimal.ROUND_HALF_UP);
            } else if (expected instanceof BigDecimal) {
                expected = ((BigDecimal) expected).setScale(actualScale, BigDecimal.ROUND_HALF_UP);
            }

            // 期待値のログ出力値を編集
            expectedValue4Log = ((BigDecimal) expected).toString();

            // 期待値・実行値のデータ型を編集
            expectedType4Log = expected == null ? "NULL" : expected.getClass().toString();
            actualType4Log = actual == null ? "NULL" : actual.getClass().toString();

        } else if (actual instanceof Exception) {
            // 実行値が例外の場合

            // 期待値・実行値のデータ型を編集
            expectedType4Log = expected == null ? "NULL" : expected.getClass().toString();
            actualType4Log = actual == null ? "NULL" : actual.getClass().toString();

            //例外同士をassertEqualすると「==」で評価されるため「フルネーム：メッセージ」の文字列に変換する
            actual = ((Exception) actual).getMessage();
            expected = ((Exception) expected).getMessage();

            // 実行値・期待値のログ出力値を編集
            actualValue4Log = ((String) actual).replaceFirst(".+:\\s", "");
            expectedValue4Log = ((String) expected).replaceFirst(".+:\\s", "");
        }

        this.printDebug(name, expectedValue4Log, expectedType4Log, actualValue4Log, actualType4Log);

        // データ照合を無視するか
        boolean isIgnore = expected != null && expected.toString().equals("IGNORE");

        // 任意値検証を行うか
        boolean isNotNull = expected != null && expected.toString().equals("NOT NULL");

        // 非同値検証を行うか
        boolean isNotEqual = expected != null && expected.toString().matches("NOT EQUAL\\[.*\\]");

        if (!isIgnore) {
            if (isNotNull) {
                assertNotNull(name, expected);
            } else if (isNotEqual) {
                assertNotSame(name, expected.toString().substring(10, expected.toString().length() - 1), actual);
            } else {
                assertEquals(name, expected, actual);
            }
        }
    }

    /**
     * テーブルのテストログ出力と同値検証.<br>
     * テーブル行数・テーブル列数・各セルの同値検証を行います。
     * 
     * @param expected 期待値テーブル
     * @param actual 実行値テーブル
     */
    protected final void collate(ITable expected, ITable actual) throws Exception {

        // 明細数を照合
        this.collate("明細数", expected.getRowCount(), actual.getRowCount());

        // 列数を照合
        Column[] expectedCols = expected.getTableMetaData().getColumns();
        Column[] actualCols = actual.getTableMetaData().getColumns();
        this.collate("フィールド数", expectedCols.length, actualCols.length);

        // 期待値テーブルでループ
        for (int i = 0; i < expected.getRowCount(); i++) {
            this.logger.debug("  <" + (i + 1) + "行目>");
            for (int j = 0; j < expectedCols.length; j++) {
                String colName = expectedCols[j].getColumnName();
                Object rightValue = expected.getValue(i, colName);
                Object testValue = actual.getValue(i, colName);
                this.collate(colName, rightValue, testValue);
            }
        }
    }

    /**
     * 期待値テーブルと実行値Listのテストログ出力と同値検証.<br>
     * 
     * @param expected 期待値テーブル
     * @param actual 実行値のList
     */
    protected final void collate(ITable expected, List actual) throws Exception {

        //明細数のチェック
        this.collate("明細数", expected.getRowCount(), actual.size());

        // 期待値テーブルの行でループ
        for (int i = 0; i < expected.getRowCount(); i++) {
            this.logger.debug("  <" + (i + 1) + "行目>");
            this.collate(expected, i, actual.get(i));
        }
    }

    /**
     * 期待値テーブルの指定された行と実行値Beanのテストログ出力と同値検証.<br>
     * 
     * @param expected 期待値テーブル
     * @param row 期待値テーブル上の期待値行番号
     * @param actual 実行値Bean
     */
    protected final void collate(ITable expected, int row, Object actual) throws Exception {

        // 期待値テーブルの列でループ
        Column[] cols = expected.getTableMetaData().getColumns();
        for (int j = 0; j < cols.length; j++) {

            String colName = cols[j].getColumnName();
            Object rightValue = expected.getValue(row, colName);

            Field field = actual.getClass().getDeclaredField(colName);
            field.setAccessible(true);
            Object testValue = field.get(actual);

            this.collate(colName, rightValue, testValue);
        }
    }

}
