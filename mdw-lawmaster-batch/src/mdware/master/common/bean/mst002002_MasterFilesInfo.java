package mdware.master.common.bean;

import java.io.*;
import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author H.Yamamoto
 * @version 1.0
 */

public class mst002002_MasterFilesInfo {
	private static final StcLog stcLog = StcLog.getInstance();
	private boolean isInit = false;
	private static Map FilesInfoMap = new HashMap();
	private static final mst002002_MasterFilesInfo INSTANCE = new mst002002_MasterFilesInfo();

	/**
	 * コンストラクタ。シングルトンを実現する。
	 */
	private mst002002_MasterFilesInfo() {
		init();
	}

	/**
	 * シングルトンを実現するためのインスタンス化メソッド。
	 * @return FilesInfos このクラスをインスタンス化したもの
	 */
	public static mst002002_MasterFilesInfo getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ファイル情報の初期化を行う。
	 * @param propertyFile パス情報の入ったファイル名
	 * @return 初期化に成功したかどうか
	 */
	public boolean init()
	{
		String propertyFile = StcLibProperty.propertiesDir + "/masterFilesInfo.properties";
		
		if( isInit )
			return true;

		if( StcLibProperty.propertiesDir == null || StcLibProperty.propertiesDir.length() == 0 )
			return false;

		File fl = new File( propertyFile );
		if( !fl.exists() )
		{
			System.err.println(fl.toString() + "が存在しないため起動できません。ファイルを確認後、再起動してください。");
			stcLog.getSysLog().fatal(fl.toString() + "が存在しないため起動できません。ファイルを確認後、再起動してください。");
			try
			{
				throw new IllegalArgumentException(fl.toString() + "が存在しないため起動できません。ファイルを確認後、再起動してください。");
			}
			catch(IllegalArgumentException iae)
			{
				iae.printStackTrace();
				throw iae;
			}
		}

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try
		{
			fis = new FileInputStream( propertyFile );
			isr = new InputStreamReader( fis, jp.co.vinculumjapan.stc.util.properties.StcLibProperty.propertyFilesEncoding );
			br = new BufferedReader( isr );
			String line = "";
			while( ( line = br.readLine() ) != null )
			{
				line = line.trim();
				if( line.startsWith("#") )
					continue;
				StringTokenizer st = new StringTokenizer(line,",",false);
				String key = "";
				String filesPath = "";
				String info = "";
				if( st.hasMoreTokens() )
					key = st.nextToken();
				else
					continue;
				if( st.hasMoreTokens() )
					filesPath = st.nextToken();
				else
					continue;
				if( st.hasMoreTokens() )
					info = st.nextToken();
				else
				{
					stcLog.getSysLog().debug(line + "をFilesInfoに登録できませんでした。");
					continue;
				}
//				stcLog.getSysLog().fatal("key=" + key + " filePath=" + filesPath + " info=" + info);
				FilesInfoMap.put(key, new mst002001_MasterFilesInfoProperties(key,filesPath,info));
			}
		}
		catch(IOException ioe)
		{
			return false;
		}
		finally
		{
			try{
				br.close();
			}catch(IOException e){}
			try{ isr.close(); }catch(Exception e){}
			try{ fis.close(); }catch(Exception e){}
		}
		isInit = true;

		return true;
	}

	/**
	 * keyで指定された情報を含んだクラスFilesInfoPropertiesを返す。
	 * @param key FilesInfoPropertiesの検索キー
	 * @return FilesInfoProperties
	 */
	public mst002001_MasterFilesInfoProperties getFilesInfoProperties( String key )
	{
		return (mst002001_MasterFilesInfoProperties)FilesInfoMap.get(key.trim());
	}

	/**
	 * keyで指定されたFilesInfoPropertiesの情報やメッセージを返す。
	 * @param key FilesInfoPropertiesの検索キー
	 * @return String 情報やメッセージ
	 */
	public String getInfo( String key )
	{
		mst002001_MasterFilesInfoProperties infop = (mst002001_MasterFilesInfoProperties)FilesInfoMap.get(key.trim());
		if( infop == null )
			throw new NullPointerException(key + "でFilesInfoを検索しましたが見つかりませんでした。");
		return (String)infop.getInfo();
	}

	/**
	 * keyで指定されたFilesInfoPropertiesのファイルパスを返す。
	 * @param key FilesInfoPropertiesの検索キー
	 * @return String 説明
	 */
	public String getFilesPath( String key )
	{
		mst002001_MasterFilesInfoProperties infop = (mst002001_MasterFilesInfoProperties)FilesInfoMap.get(key.trim());
		if( infop == null )
			throw new NullPointerException(key + "でFilesInfoを検索しましたが見つかりませんでした。");
		return (String)infop.getFilesPath();
	}

	/**
	 * FilesInfoPropertiesの入ったMapを返す。
	 * @return Map
	 */
	public Map getFilesInfoMap()
	{
		return FilesInfoMap;
	}
}
