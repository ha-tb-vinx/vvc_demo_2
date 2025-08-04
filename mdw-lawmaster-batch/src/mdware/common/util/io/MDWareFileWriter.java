package mdware.common.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * RBSITE用ユーティリティー（テキストファイル書き込み）。 <BR>
 * ファイルを書き込む時、通常はFileWriterとBufferedWriterを利用し書き込んでいく。<BR>
 * このクラスは、ファイルのエンコード文字列を指定し書き込む事が出来る。<BR>
 * FileWriter+BufferedWriter、FileOutputStream+OutputStreamWriter+BufferedWriterの<BR>
 * 組み合わせでファイル書き込みをする時より簡易に利用出来るようにしている。<BR>
 * ・改行文字列をセットする事が出来る。(デフォルトは\r\n)<BR>
 * ・flushはクローズの時に勝手に行う。<BR>
 * 例：<BR>
 * <PRE>
 * RbsiteFileWriter rfw = null;
 * try
 * {
 * 　　rfw = new RbsiteFileWriter("テキストファイルのフルパス", "UTF-8");
 * 　　rfw.setNewListString("\r\n");
 * 　　rfw.writeLine("あいう");
 * 　　rfw.writeLine("かきく");
 * }
 * finally
 * {
 * 　　try{ rfw.close()+ }catch(Exception e){}
 * }
 * </PRE>
 * @author telema_yugen777
 */
public class MDWareFileWriter
{
	private FileOutputStream fos = null;

	private OutputStreamWriter osw = null;

	private BufferedWriter bw = null;

	private String newLine = "\r\n";

	/**
	 * コンストラクタ
	 * @param file 書き出すファイル
	 * @throws IOException
	 */
	public MDWareFileWriter( File file ) throws IOException
	{
		try
		{
			fos = new FileOutputStream( file );
			osw = new OutputStreamWriter( fos );
			bw = new BufferedWriter( osw );
		}
		catch( IOException ioe )
		{
			try
			{
				bw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				osw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fos.close();
			}
			catch( Exception e )
			{
			}
			bw = null;
			osw = null;
			fos = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 書き出すファイルのフルパス
	 * @param encStr エンコード文字列
	 * @throws IOException
	 */
	public MDWareFileWriter( File file, String encStr ) throws IOException
	{
		try
		{
			fos = new FileOutputStream( file );
			osw = new OutputStreamWriter( fos, encStr );
			bw = new BufferedWriter( osw );
		}
		catch( IOException ioe )
		{
			try
			{
				bw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				osw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fos.close();
			}
			catch( Exception e )
			{
			}
			bw = null;
			osw = null;
			fos = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 書き出すファイル
	 * @throws IOException
	 */
	public MDWareFileWriter( String file ) throws IOException
	{
		try
		{
			fos = new FileOutputStream( file );
			osw = new OutputStreamWriter( fos );
			bw = new BufferedWriter( osw );
		}
		catch( IOException ioe )
		{
			try
			{
				bw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				osw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fos.close();
			}
			catch( Exception e )
			{
			}
			bw = null;
			osw = null;
			fos = null;
			throw ioe;
		}
	}

	/**
	 * コンストラクタ
	 * @param file 書き出すファイルのフルパス
	 * @param encStr エンコード文字列
	 * @throws IOException
	 */
	public MDWareFileWriter( String file, String encStr ) throws IOException
	{
		try
		{
			fos = new FileOutputStream( file );
			osw = new OutputStreamWriter( fos, encStr );
			bw = new BufferedWriter( osw );
		}
		catch( IOException ioe )
		{
			try
			{
				bw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				osw.close();
			}
			catch( Exception e )
			{
			}
			try
			{
				fos.close();
			}
			catch( Exception e )
			{
			}
			bw = null;
			osw = null;
			fos = null;
			throw ioe;
		}
	}

	/**
	 * 改行文字列をセットする。
	 * writeLineを呼んだ後に改行文字列が自動で付加される。
	 * このメソッドを呼んだ時は、改行は付加されない。
	 * @param newLine　改行文字列
	 */
	public void setNewListString( String newLine )
	{
		this.newLine = newLine;
	}

	/**
	 * 文字を書き出す。
	 * @param c　文字
	 * @throws IOException
	 */
	public void write( char c ) throws IOException
	{
		bw.write( new char[] { c } );
	}

	/**
	 * 文字列を書き出す。
	 * @param str 文字列
	 * @throws IOException
	 */
	public void write( String str ) throws IOException
	{
		bw.write( str );
	}

	/**
	 * 文字列＋改行文字列を書き出す。
	 * @param str　文字列
	 * @throws IOException
	 */
	public void writeLine( String str ) throws IOException
	{
		StringBuffer sb = new StringBuffer( str );
		sb.append( newLine );
		bw.write( sb.toString() );
	}

	/**
	 * 改行文字列を書き出す。
	 * @throws IOException
	 */
	public void writeNewLine() throws IOException
	{
		bw.write( newLine );
	}

	/**
	 * ファイルを閉じる。
	 * flushを行いファイルを閉じる
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		try
		{
			bw.flush();
		}
		catch( Exception e )
		{
		}
		try
		{
			bw.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			osw.flush();
		}
		catch( Exception e )
		{
		}
		try
		{
			osw.close();
		}
		catch( Exception e )
		{
		}
		try
		{
			fos.flush();
		}
		catch( Exception e )
		{
		}
		try
		{
			fos.close();
		}
		catch( Exception e )
		{
		}
		bw = null;
		osw = null;
		fos = null;
	}
}