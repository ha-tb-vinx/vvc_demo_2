package mdware.common.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RBSITE用ユーティリティー（テキストファイル読み込み）。 <BR>
 * ファイルを読み込む時、通常はFileReaderとBufferedReaderを利用し読み込んでいく。<BR>
 * このクラスは、ファイルのエンコード文字列を指定し読み込む事が出来る。<BR>
 * FileReader+BufferedReader、FileInputStream+InputStreamReader+BufferedReaderの組み合わせで<BR>
 * ファイル読み込みをする時より簡易に利用出来るようにしている。<BR>
 * 例：<BR>
 * <PRE>
 * RbsiteFileReader rfr = null;
 * try
 * {
 * 　　rfr = new RbsiteFileReader("テキストファイルのフルパス", "UTF-8");
 * 　　String line = null;
 * 　　while( ( line = rfr.readLine() ) != null )
 * 　　{
 * 　　　　:
 * 　　}
 * }
 * finally
 * {
 * 　　try{ rfr.close()+ }catch(Exception e){}
 * }
 * </PRE>
 * @author telema_yugen777
 */
public class MDWareFileReader
{
	private FileInputStream fis = null;

	private InputStreamReader isr = null;

	private BufferedReader br = null;

	/**
	 * コンストラクタ
	 * @param file 読み込むファイル
	 * @throws IOException
	 */
	public MDWareFileReader( File file ) throws IOException
	{
		try
		{
			fis = new FileInputStream( file );
			isr = new InputStreamReader( fis );
			br = new BufferedReader( isr );
		}
		catch( IOException ioe )
		{
			try
			{
				br.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				isr.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fis.close();
			}
			catch( Exception e )
			{
			}
			br = null;
			isr = null;
			fis = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 読み込むファイルのフルパス
	 * @throws IOException
	 */
	public MDWareFileReader( String file ) throws IOException
	{
		try
		{
			fis = new FileInputStream( file );
			isr = new InputStreamReader( fis );
			br = new BufferedReader( isr );
		}
		catch( IOException ioe )
		{
			try
			{
				br.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				isr.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fis.close();
			}
			catch( Exception e )
			{
			}
			br = null;
			isr = null;
			fis = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 読み込むファイル
	 * @param encStr エンコード文字列
	 * @throws IOException
	 */
	public MDWareFileReader( File file, String encStr ) throws IOException
	{
		try
		{
			fis = new FileInputStream( file );
			isr = new InputStreamReader( fis, encStr );
			br = new BufferedReader( isr );
		}
		catch( IOException ioe )
		{
			try
			{
				br.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				isr.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fis.close();
			}
			catch( Exception e )
			{
			}
			br = null;
			isr = null;
			fis = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 読み込むファイル
	 * @param encStr エンコード文字列
	 * @throws IOException
	 */
	public MDWareFileReader( String file, String encStr ) throws IOException
	{
		try
		{
			fis = new FileInputStream( file );
			isr = new InputStreamReader( fis, encStr );
			br = new BufferedReader( isr );
		}
		catch( IOException ioe )
		{
			try
			{
				br.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				isr.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fis.close();
			}
			catch( Exception e )
			{
			}
			br = null;
			isr = null;
			fis = null;
			throw ioe;
		}
	}

	/**
	 * ファイルの一行を返す
	 * @return ファイル内の一行
	 * @throws IOException
	 */
	public String readLine() throws IOException
	{
		return br.readLine();
	}

	/**
	 * 開いたファイルを閉じる
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		try
		{
			br.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			isr.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			fis.close();
		}
		catch( Exception e )
		{
		}
		br = null;
		isr = null;
		fis = null;
	}

	/**
	 * ガベージコレクションの対象となった時、閉じ忘れを考慮しファイルを閉じる
	 */
	protected void finalize()
	{
		try
		{
			br.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			isr.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			fis.close();
		}
		catch( Exception e )
		{
		}
		br = null;
		isr = null;
		fis = null;
	}
}