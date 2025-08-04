package mdware.batch.retailif;

import java.util.*;
import java.sql.*;
import java.text.ParseException;
import java.io.*;

import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.log.StcLog;

import mdware.common.batch.bean.*;
import mdware.common.batch.log.BatchLog;
import mdware.batch.filedb.*;
import mdware.common.batch.util.convert.DataExchanger;
import mdware.common.batch.util.file.*;
import mdware.common.batch.util.file.record.*;
import mdware.common.bean.*;
import mdware.common.util.*;

/**
 * <p>タイトル: 小売インターフェース処理</p>
 * <p>説明: ファイル集配信処理を行います</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.00 
 */

public class RetailInterface {
	public static final String MODE_COLLECT    = FunctionBeanHolder.HOST_UPLOAD;   //集信
	public static final String MODE_DISTRIBUTE = FunctionBeanHolder.HOST_DOWNLOAD; //配信
	//拡張子
	public static final String SUFFIX_DATA = ".dat";
	public static final String SUFFIX_FLG  = ".end";
	public static final String SUFFIX_BAK  = ".zip";

	protected FunctionBeanHolder funcBh = null;
	protected SystemConfBeanHolder sysBh = null;
	protected RetailBeanHolder retailBean = null;
	protected DataBase dataBase = null;

	protected String longNow = DateUtility.longNow(); //現在日時
	protected String today   = DateUtility.today(); //現在日付
	protected BatchLog batchLog = BatchLog.getInstance();
	protected String dataSyubetuCd   = null;
}
