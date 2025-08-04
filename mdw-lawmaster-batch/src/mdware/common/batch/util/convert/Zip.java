package mdware.common.batch.util.convert;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 *
 * <p>タイトル: ZIP形式の圧縮ファイルのエンコード＆デコード</p>
 * <p>説明: ZIP形式のファイルの圧縮と解凍を行ないます<BR>
 * ※元のファイルをZIP形式に変換する事を「圧縮（エンコード）」と呼びます。<BR>
 * その逆に、ZIP形式ファイルを元のファイルに変換することを「解凍（デコード）」と呼びます。<BR>
 * </p>
 * @version 1.0
 */
public class Zip
{

	private Vector m_Vector = new Vector(); // 圧縮元ファイルを格納する場所

	// コンストラクタ
	/**
	 * １ファイルだけをエンコード、又はファイルをデコードする場合はインスタンス化は不要<BR>
	 * encodeZip( String src_filename, String zip_filename );又は<BR>
	 * decodeZip( String zip_filename, String dir_filename );を使用する<BR>
	 * 複数ファイルをエンコードする場合のみ、インスタンス化が必要<BR>
	 */
	public Zip()
	{
		m_Vector.clear();
	}


	/**
	 * ZIPファイルを解凍（デコード）する<BR>
	 * ※但し、ディレクトリ付きZIPファイルには未対応です
	 *
	 * @param zip_filename ZIPファイル名
	 * @param dir_filename 解凍先ディレクトリ(ex. "c:/tmp/")
	 * @return 正常終了時は true を返します。異常終了時は false を返します
	 */
	public static boolean decodeZip( String zip_filename, String dir_filename )
	{
		String tgt_Dir = "";
		String makeFilename = "";
		long makeFilesize = 0;

		if ( zip_filename.equals("") || null == zip_filename ){ return( false ); }
		if ( null == dir_filename ){
			tgt_Dir = "";
		} else {
			tgt_Dir = dir_filename;
		}

		try {
			ZipInputStream zis = new ZipInputStream( new FileInputStream( zip_filename ) );

			ZipEntry zentry = null;
		    int nByteRead = 0;
			while( true ){
				int t_size = 0;

				zentry = zis.getNextEntry();
				if( null == zentry ){ break; }

				File cfile = new File( zentry.getName() );
				makeFilesize = zentry.getSize();

				makeFilename = dir_filename + cfile.getName();
				File out_fclass = new File( makeFilename );
				out_fclass.delete();

				FileOutputStream fos = new FileOutputStream( out_fclass );
				BufferedOutputStream bos = new BufferedOutputStream( fos );

				byte[] bBuff = new byte[1024];

				long lFileOffset = 0;
				long ltmp = 0;

				//20030122 @UPD yamanaka sizeがうまくとれないときがあるため変更
				while( (nByteRead = zis.read(bBuff)) != -1 ){
				  bos.write( bBuff, 0, nByteRead );
				  bos.flush();
				  t_size += nByteRead;
				}
				/**
				while( lFileOffset < makeFilesize )
				{
					ltmp = zis.read( bBuff, 0, 1024 );
					if ( 1 > ltmp ){ break; }
					fos.write( bBuff, 0, (int)ltmp );
					lFileOffset += ltmp;
				}
				*/
				//fos.flush();
				fos.close();
				zis.closeEntry();
			}
			zis.close();

		}
		catch(Exception e)
		{
			System.out.println("decodeZip 例外発生[" + e + "]");
			e.printStackTrace();
			return( false );
		}
		return( true );
	}


	/**
	 * 圧縮ファイルに入れるファイル名を、メンバー変数に追加する<BR>
	 * @param src_filename 圧縮元のファイル名
	 */
	public void setSrcFilename( String src_filename )
	{
		m_Vector.addElement(src_filename);
	}

	/**
	 * セットしてある圧縮元のファイル名をクリアする
	 */
	public void clearSrcFile()
	{
		m_Vector.clear();
	}


	/**
	 * セットしてある圧縮元のファイル名の数を返す
	 * @return セットしてある圧縮元のファイル名の数 0～
	 */
	public int size()
	{
		return( m_Vector.size() );
	}


	/**
	 * 普通のファイルからZIP形式の圧縮ファイルを生成します。<BR>
	 * （１ファイル用 使い方簡単です）
	 * 圧縮後のZIPファイル名が、null又は空白であれば、<BR>
	 * 圧縮元のファイルに[.zip]を追加したものになります。<BR>
	 * <BR>
	 * @param src_filename 圧縮元のファイル<BR>
	 * @param zip_filename 圧縮後のZIPファイル名<BR>
	 * @return 正常終了時は圧縮後のZIPファイル名を返します。<BR>
	 * 異常終了時は null を返します
	 */
	public static String encodeZip( String src_filename, String zip_filename )
	{
		String ret_filename = "";

		if ( src_filename.equals("") || null == src_filename ){ return( null ); }
		if ( zip_filename.equals("") || null == zip_filename ){
			ret_filename = src_filename + ".zip";
		} else {
			ret_filename = zip_filename;
		}

		// 書き込み開始
		try {
			File out_fclass = new File( ret_filename );
			out_fclass.delete();

			ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( ret_filename ) );
			zos.setMethod(ZipOutputStream.DEFLATED);

			File fclass = new File( src_filename );

			addTargetFile( zos, fclass );

			zos.finish();
			zos.close();
		}
		catch(Exception e)
		{
			System.out.println("encodeZip 例外発生[" + e + "]");
			e.printStackTrace();
			return( null );
		}
		return( ret_filename );
	}


	/**
	 * メンバー変数にセットしてある圧縮元ファイルから、<BR>
	 * ZIP形式の圧縮ファイルを生成します。<BR>
	 * （多ファイル用）<BR>
	 * <BR>
	 * （例）コーディングサンプル<BR>
	 * Zip czip = new Zip();<BR>
	 * czip.setSrcFilename = "test1.txt";<BR>
	 * czip.setSrcFilename = "test2.txt";<BR>
	 * czip.encodeZip("test.zip");<BR>
	 * <BR>
	 * @param zip_filename 圧縮ファイル名<BR>
	 * @return 正常終了時は true を返します。異常終了時は false を返します
	 */
	public boolean encodeZip( String zip_filename )
	{
		String ret_filename = "";

		if ( zip_filename.equals("") || null == zip_filename ){ return( false ); }

		// 書き込み開始
		try {
			File out_fclass = new File( zip_filename );
			out_fclass.delete();

			ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( zip_filename ) );

			File fclass = null;
			int iloop = 0;
			while( iloop < m_Vector.size() )
			{
				fclass = new File( m_Vector.elementAt(iloop).toString() );
				addTargetFile( zos, fclass );
				iloop++;
			}
			zos.finish();
			zos.close();
		}
		catch(Exception e)
		{
			System.out.println("encodeZip 例外発生[" + e + "]");
			e.printStackTrace();
			return( false );
		}
		return( true );
	}




	private static void addTargetFile( ZipOutputStream zos, File file )
								throws FileNotFoundException,
								ZipException,IOException
	{
		try {
			BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
// 20030120 @UPD yamanaka start
//			ZipEntry target = new ZipEntry( file.getPath() );
			ZipEntry target = new ZipEntry( file.getName() );
// 20030120 @UPD yamanaka end
			String st="";
			st = target.getName();
			zos.putNextEntry( target );  // target の書き込み開始

			byte buf[] = new byte[1024];
			int icount = bis.read( buf, 0, 1024 );
			while( 0 < icount  ) {
				zos.write( buf, 0, icount );
				icount = bis.read( buf, 0, 1024 );
			}
			bis.close();
			zos.closeEntry();  // target の書き込み終了
		}
		catch(Exception e)
		{
			System.out.println("encodeZip write 例外発生[" + e + "]");
			e.printStackTrace();
		}
	}
}