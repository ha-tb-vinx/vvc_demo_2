package mdware.common.batch.util.properties;

import java.io.File;
import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import jp.co.vinculumjapan.stc.util.servlet.urls.*;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.*;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */

public class BatchProperty
{
	public static final String DEBUG_LOG4J_FILE = "stcliblog4j_debug.properties";
	public static final String DEBUG_DATABASE_FILE = "database_debug.properties";
//	private static final String RELEASE_LOG4J_FILE = "stcliblog4j.properties";
	public static final String RELEASE_LOG4J_FILE = "stcliblog4j.properties"; //20030120 @UPD yamanaka これだけ外から呼べるようにする。
	private static final String RELEASE_DATABASE_FILE = "database.properties";
	private static final String jobsFile = "batchJob.properties";
	private static final StcLog stcLog = StcLog.getInstance();
	private static final CollectConnections collectConnections = CollectConnections.getInstance();
	private static final Jobs jobs = Jobs.getInstance();
	public static String propertiesDir = null;
	private static Object[][] contents = null;

	public BatchProperty(String executeMode, String databaseUse )
	{
		String log4jFile = DEBUG_LOG4J_FILE;
		String databaseFile = DEBUG_DATABASE_FILE;
		boolean isDBUse = false;
		if( executeMode != null && executeMode.trim().toLowerCase().equals("release") )
		{
			log4jFile = RELEASE_LOG4J_FILE;
			databaseFile = RELEASE_DATABASE_FILE;
		}
		if( databaseUse != null && !databaseUse.trim().toLowerCase().equals("no") )
		{
			isDBUse = true;
			if( databaseUse.equals("use") )
				collectConnections.setUsePool(false);
		}
		System.out.println("使用するLog4Jの設定ファイルは" + log4jFile + "です。");
		if( isDBUse )
		{
			System.out.println("使用するDatabaseの設定ファイルは" + databaseFile + "です。");
			if( databaseUse.equals("use") )
				System.out.println("コネクションプールは使用しません。");
			else
				System.out.println("コネクションプールを使用します。");
		}
		else
		{
			System.out.println("Databaseは使用しません。");
		}
		init( log4jFile, databaseFile, isDBUse );
		setContents();
	}
	private void setContents()
	{
		contents = new Object[][] {
			{"Log4J", stcLog},
			{"Database", collectConnections},
		};
	}
	protected Object[][] getContents()
	{
		return contents;
	}

	private void init(String log4jFile, String databaseFile, boolean isDBUse )
	{
		if( this.propertiesDir == null )
		{
			System.out.println("BatchProperty.propertiesDirの未設定");
			throw new NullPointerException("BatchProperty.propertiesDirの未設定");
		}
		this.initLog4j( log4jFile );
		this.initJobs();
		if( isDBUse )
			this.initCollectConnections( databaseFile );
	}

	/**
	 * stclibJob.propertiesからJobsを読み込み初期化する
	 * @return boolean Jobsの初期化が成功したかどうかを返す。
	 */
	private boolean initJobs()
	{
		return jobs.initFromFile(this.propertiesDir + "/" + this.jobsFile);
	}

	/**
	 * Log4jの初期化
	 * @return boolean Log4jの初期化が成功したかどうかを返す。
	 */
	private boolean initLog4j( String log4jFile ) {
		String log4jPath = this.propertiesDir + "/" + log4jFile;
		return StcLog.getInstance().init(log4jPath) && BatchLog.getInstance().init(log4jPath) && BatchUserLog.init(log4jPath);
	}

	/**
	 * CollectionConnectionPoolの初期化。
	 * コネクションプールを初期化に失敗した時は別の方法でＤＢ接続を行う方法を作成しておいたほうが懸命です。
	 * @return boolean コネクションプールの初期化に成功したかどうかを返す。
	 */
	private boolean initCollectConnections( String dbFile ) {

		// CollectionConnectionPoolの設定ファイルの絶対パスを取得する。
		String file = this.propertiesDir + "/" + dbFile;

		// ファイルが定義されていない時は無視する。
		if( file == null )
		{
			stcLog.getSysLog().info("(database-connection-file)が設定されていないためデータベースは使用できません。");
			collectConnections.setUsePool( false );
			return false;
		}

		// ファイルが定義されているので初期化を行う。
		return collectConnections.init( file );
	}
}
