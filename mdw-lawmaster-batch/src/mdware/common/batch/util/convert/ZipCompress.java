package mdware.common.batch.util.convert;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import mdware.common.bean.*;
/**
 * <p>タイトル: ZIP形式圧縮クラス</p>
 * <p>説明: 対象ファイルをZIP形式で圧縮します。<br>
 *    使い方：XXX.encodeZip(ファイルパスまたはディレクトリパス); </p>
 * <p>著作権: Copyright (c) 2002-2003</p>
 * <p>会社名: Vinculum Japan Corporation.</p>
 * @author A.Tashiro
 * @version 1.0
 */

public class ZipCompress {

     // ファイルの読み込みの終わりを表す定数 */
     protected static final int EOF = -1;

     // FILE オブジェクト
      protected File dir;
     // 対象のファイル/ディレクトリ
      protected String parentName;

      // コンストラクタ
      public ZipCompress( ) {
          this.parentName = "";
          this.dir = null;
      }

      public ZipCompress( String parentName, File dir ) {

          this.parentName = parentName;
          this.dir = dir;
      }

      //圧縮
      public static boolean encodeZip( String parentNamefile) {
          try {
                // 対象ファイル
                File file = new File( parentNamefile);
                // 圧縮ファイル
                File zipFile = new File( parentNamefile + ".zip" );
                ZipOutputStream zos
                    = new ZipOutputStream(
                           new FileOutputStream( zipFile ) );

                if( file.isFile() ) {

                      addTargetFile( zos, file );
                }
                else if( file.isDirectory() ) {

                     ZipCompress origin = new ZipCompress( parentNamefile, file );
                     Vector vec = origin.pathNames();
                     Enumeration enu = vec.elements();
                     while( enu.hasMoreElements() ) {
                          File f = new File(
                              (String)enu.nextElement() );
                          addTargetFile( zos, f );
                     }
                }
                zos.close();
           }
           catch( FileNotFoundException e ){
                  return false;
           }
           catch( ZipException e ){
                  return false;
           }
           catch( IOException e ){
                  return false;
           }
            return true;
      }
      // ディレクトリとファイル名の一覧を返す
      protected Vector pathNames()
                throws FileNotFoundException, IOException {

          try {
            // ディレクトリ内を調べる

               String[] list = dir.list();
               File[] files = new File[list.length];
               Vector vec = new Vector();
               for( int i=0; i<list.length; i++ ) {

                    String pathName = parentName
                           + File.separator + list[i];
                    files[i] = new File( pathName );
                    if( files[i].isFile() )
                        vec.addElement( files[i].getPath() );
               }

             // サブディレクトリを調べる
               for( int i=0; i<list.length; i++ ) {

                  if( files[i].isDirectory() ) {

                        String name = parentName
                           + File.separator + list[i];
                        ZipCompress sub = new ZipCompress( name, files[i] );
                        Vector sv = sub.pathNames();
                        Enumeration enu = sv.elements();
                        while( enu.hasMoreElements() )
                          vec.addElement( enu.nextElement() );
                    }
               }
               return vec;
          }
          catch( FileNotFoundException e ){
                 throw e;
          }
          catch( IOException e ){
                 throw e;
          }
     }

    // 与えられたファイルを圧縮し、Zipファイルに追加する
     public static void addTargetFile(
                         ZipOutputStream zos, File file )
                         throws FileNotFoundException,
                                ZipException,IOException {
          try {
               BufferedInputStream bis
                     = new BufferedInputStream(
                           new FileInputStream( file ) );
//               ZipEntry target = new ZipEntry( file.getPath() );
               ZipEntry target = new ZipEntry( file.getName() );
               zos.putNextEntry( target );  // target の書き込み開始

               byte buf[] = new byte[1024];
               int count;
               while( ( count = bis.read( buf, 0, 1024 ) ) != EOF ) {
                    zos.write( buf, 0, count );
               }

               bis.close();
               zos.closeEntry();  // target の書き込み終了
          }
          catch( FileNotFoundException e ){
                 throw e;
          }
          catch( ZipException e ){
                 throw e;
          }
          catch( IOException e ){
                 throw e;
          }

  }

}