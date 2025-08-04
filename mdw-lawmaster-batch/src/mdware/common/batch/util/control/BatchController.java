package mdware.common.batch.util.control;

import java.lang.reflect.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.*;
import mdware.common.batch.util.properties.BatchProperty;

/**
 * <P>タイトル:  バッチジョブ実行クラス</P>
 * <P>説明: バッチＪＯＢをする。</P>
 * <P>著作権:	Copyright (c) 2001</P>
 * <P>会社名:	Vinculum Japan Corp.</P>
 * <PRE>
 *
 * </PRE>
 * @author Yamanaka
 * @version 1.0
 * @version 3.0 2003.11.06 ログ出力内容変更
 * @version 3.1 2004.09.06 リターンコード対応
 */

public class BatchController {
    private static final StcLog stcLog = StcLog.getInstance();
    private static final BatchLog batchLog = BatchLog.getInstance();
    private static boolean initilized = false;
	private static boolean isError = false;

    /**
     * 初期処理を行います。（必須）
     * @param propertyDir
     * @param executeMode
     * @param databaseUse
     */
    public synchronized void init(String propertyDir, String executeMode, String databaseUse) {
        if (initilized) {
            return;
        }

        if( propertyDir == null || propertyDir.trim().length() == 0 )
            throw new NullPointerException("プロパティーファイルの入ったディレクトリ(property-dir)を指定してください。");
        BatchProperty.propertiesDir = propertyDir;
// FUJI_POSF OTSUKA MARGED START jp.co.vinculumjapan.stc.util.db.CollectConnectionsはBatchPropertyだけでなく、StcLibPropertyも設定が必要。
        StcLibProperty.propertiesDir = BatchProperty.propertiesDir;
// FUJI_POSF OTSUKA MARGED END
        //DB接続の初期化
        new BatchProperty(executeMode, databaseUse);
        initilized = true;
    }

    /**
     * 指定されたバッチジョブを実行します。
     * @param batchJobID バッチ実行用ジョブＩＤ
     * @param args バッチ実行時引数。必要ない場合やファイルに引数情報を持たせている場合はnullを指定して下さい。
     */
    public void callBatchProcess(String batchJobID, String[] args) {

        if (batchJobID != null || batchJobID.trim().length() == 0) {
            stcLog.getSysLog().debug("batchJobID: " + batchJobID + "の実行要求を取得しました。");
        }
        else {
            stcLog.getSysLog().fatal("batchJobIDの取得に失敗しました。" );
			isError = true;
            return;
        }

        /**
        　* Job全体情報を取得
        　*/
        Jobs jobs = Jobs.getInstance();

        if (jobs != null) {
            stcLog.getSysLog().debug("batchJob全体情報を取得しました。");
        }
        else {
            stcLog.getSysLog().fatal("batchJob全体情報取得エラーが発生しました。batchJob全体情報定義が取得できていない可能性があります。" );
			isError = true;
            return;
        }

        /**
        　* Job情報を取得
        　*/
        JobProperties jobProperties = jobs.get(batchJobID);

        if (jobProperties != null) {
            stcLog.getSysLog().debug("batchJob情報を取得しました。=> " + jobProperties.toString());
        }
        else {
            stcLog.getSysLog().fatal("batchJobID: " + batchJobID + " のJob情報取得時にエラーが発生しました。batchJob情報に登録されていない可能性がありあます。" );
			isError = true;
            return;
        }

        /**
        　* batchJobを実行するClassオブジェクトの取得
        　*/
        Class batchClass = null;

        try {
            batchClass = Class.forName(jobProperties.getClassName());
        } catch (ClassNotFoundException e) {
            stcLog.getSysLog().fatal("batchJobID: " + batchJobID + " クラス: " + jobProperties.getClassName() + "取得時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
			isError = true;
            return;
        }

        // ユーザーログ出力用にジョブIDセット
		BatchUserLog userLog = BatchUserLog.getInstance();
		userLog.setJobId(batchJobID);
        
        /**
         * Classオブジェクトのインスタンスの取得
         */
        Object batchObj = null;

        try {
            batchObj = batchClass.newInstance();
        } catch (IllegalAccessException e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのインスタンス化時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
			isError = true;
            return;
        } catch (InstantiationException e) {
            stcLog.getSysLog().fatal("class: " + jobProperties.toString() + " オブジェクトのインスタンス化時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
			isError = true;
            return;
        }

        /**
         * batchJob実行を行うメソッドの取得
         */
        Method batchMethod = null;

        try {
            batchLog.getLog().info(batchJobID, "起動処理", jobProperties.getJobName() + "処理の実行を開始します。");

            Class[] parameterTypes = null;
            int parameterLength = 0;
            //このメソッドの引数argsがnullであるかどうかによって引数がコマンドラインから与えられたものか
            //ファイルから与えられたものかどうかを判断する。
            Object[] parameters = null;
            //ログ出力用
            String outputArgs = "";
            if (args == null) {
                parameters = jobProperties.getArgs();
                parameterLength = parameters.length;
            } else {
                parameters = args;
                parameterLength = parameters.length;
            }
            if (parameterLength > 0) {
                parameterTypes = new Class[parameterLength];
                for (int i = 0 ; i < parameterLength ; i++) {
                    //パラメータに取れるものはＳｔｒｉｎｇのもののみ
                    try {
                        parameterTypes[i] = Class.forName("java.lang.String");
                    } catch (ClassNotFoundException e) {
                    }
                    outputArgs += (String)parameters[i];
                    if (i != parameterLength - 1) {
                        outputArgs += ", ";
                    }
                }
            }
            //パラメータ（引数）の出力
            if (outputArgs.trim().length() != 0) {
                batchLog.getLog().info(batchJobID, "起動処理", "パラメータ：" + outputArgs);
            }

            batchMethod = batchClass.getMethod(jobProperties.getMethodName(), parameterTypes);
        } catch (NoSuchMethodException e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
            //バッチログにエラー出力
            batchLog.getLog().error(batchJobID, "起動処理", jobProperties.getJobName() + "処理でエラーが発生しました。（指定されたパラメータが不正である可能性があります。）：" + e.toString());
			isError = true;
            return;
        } catch (SecurityException e) {
            stcLog.getSysLog().fatal("class: " + jobProperties.toString() + " オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
			isError = true;
            return;
        }
		
        /**
         * メソッドの実行（バッチ処理の実行）
         */
        if(!batchMethod.isAccessible()){
			batchMethod.setAccessible(true);
        }
        
        try {
            if (args == null) {
                batchMethod.invoke(batchObj, jobProperties.getArgs());
            } else {
                batchMethod.invoke(batchObj, args);
            }
        } catch (IllegalAccessException e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
            //バッチログにエラー出力
            batchLog.getLog().error(batchJobID, jobProperties.getJobName(), jobProperties.getJobName() + "処理でエラーが発生しました。（指定されたパラメータが不正です。）：" + e.toString());
			isError = true;
            return;
        } catch (IllegalArgumentException e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
            
            //バッチログにエラー出力
            batchLog.getLog().error(batchJobID, jobProperties.getJobName(), jobProperties.getJobName() + "処理でエラーが発生しました。（指定されたパラメータが不正です。）：" + e.toString());
			isError = true;
            return;
        } catch (InvocationTargetException e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.getTargetException().toString());
            //バッチログにエラー出力
            batchLog.getLog().error(batchJobID, jobProperties.getJobName(), jobProperties.getJobName() + "処理でエラーが発生しました。：" + e.getTargetException().toString());
			isError = true;
            return;
        } catch (Exception e) {
            stcLog.getSysLog().fatal("class:\"" + jobProperties.toString() + "\"オブジェクトのメソッド実行時に例外が発生しました。内容は以下の通りです。");
            stcLog.getSysLog().fatal(e.toString());
            //バッチログにエラー出力
            batchLog.getLog().error(batchJobID, jobProperties.getJobName(), jobProperties.getJobName() + "処理でエラーが発生しました。：" + e.toString());
			isError = true;
            return;
        }

        stcLog.getSysLog().debug("class: " + jobProperties.toString() + " の実行を終了しました。");
        //バッチログにエラー出力
        batchLog.getLog().info(batchJobID, jobProperties.getJobName(), jobProperties.getJobName() + "処理が終了しました。");
    }

    /**
     * バッチ実行用関数。本番環境にてバッチ実行時にjava.exeより実行される唯一のエントリです。
     * @param args
     */
    public static void main (String[] args) {
        String propertyDir = "defaultroot/WEB-INF/properties";
        String executeMode = "release";
        String databaseUse = "use";
        String batchJobID = null;
        String[] parameters = null;

        BatchController controller = new BatchController();

        if (args.length == 0) { //デバッグ用
            batchJobID = "MB13-01-01";
        } else if (args.length == 1) { //ジョブＩＤのみを指定
            batchJobID = args[0];
        } else if (args.length == 2) { //ジョブＩＤとプロパティディレクトリを指定。本番環境に使用できます。
            batchJobID = args[0];
            propertyDir = args[1];
        } else if (args.length == 4) { //全てのパラメータを指定。
            batchJobID = args[0];
            propertyDir = args[1];
            executeMode = args[2];
            databaseUse = args[3];
        } else if (args.length > 4) { //全てのパラメータを指定し、且つバッチ実行用に独自の引数を与えたい場合。
            batchJobID = args[0];
            propertyDir = args[1];
            executeMode = args[2];
            databaseUse = args[3];
            parameters = new String[args.length - 4];
            for (int i = 0 ; i < args.length - 4 ; i++) {
                parameters[i] = args[4 + i];
            }
        }
        //フジ特別対応。いかなる場合でもpoolでデータベース処理する
		databaseUse = "pool";
        
        controller.init(propertyDir, executeMode, databaseUse);
        controller.callBatchProcess(batchJobID, parameters);

        // エラーが発生した場合、リターンコードを(1)を戻す　　
        if (isError) {
        	System.exit(1);
        }
		System.exit(0);
    }
}
