package mdware.common.batch.util.convert;

import java.io.*;
import java.util.*;

/**
 * <p>タイトル: RB Site データ変換層処理</p>
 * <p>説明: ファイルのフォーマット変換や、コピー、圧縮等を行います</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Yamanaka
 * @version 1.00 本番開始
 * @version 1.01 20030512 yamanaka 障害報告№009対応
 */

public class DataExchanger {
	//変換モード
	public static final int NO_EXCHANGE_COPY   = 0;
	public static final int NO_EXCHANGE_APPEND = 1;
	public static final int NO_EXCHANGE_MOVE   = 2;
	public static final int JCA_TO_CSV = 3;
	public static final int CSV_TO_JCA = 4;
	public static final int CSV_TO_XML = 5;
	public static final int XML_TO_CSV = 6;
	public static final int ZIP    = 7;
	public static final int UNZIP  = 8;

	/**
	 * データ変換を行う。
	 * @param exchangeMode 変換モード
	 * @param fromFilePath 変換元ファイルパス
	 * @param toFilePath   変換後ファイルパス
	 * @return
	 */
	public static boolean exchange(int exchangeMode, String fromFilePath, String toFilePath) {
		switch (exchangeMode) {
			//変換せずにコピーのみ行う
			case NO_EXCHANGE_COPY:
				return copyFile(fromFilePath, toFilePath, false);
			//変換せずにアペンドする
			case NO_EXCHANGE_APPEND:
				return copyFile(fromFilePath, toFilePath, true);
			//変換せずに移動する
			case NO_EXCHANGE_MOVE:
				boolean moved = copyFile(fromFilePath, toFilePath, false);
				if (moved) {
					moved = new File(fromFilePath).delete(); //削除する。
				}
				return moved;
			//ＪＣＡからＣＳＶへ（今回は実装しない）
			case JCA_TO_CSV:
				return false;
			//ＣＳＶからＪＣＡへ（今回は実装しない）
			case CSV_TO_JCA:
				return false;
			//ＣＳＶからＸＭＬへ（今回は実装しない）
			case CSV_TO_XML:
				return false;
			//ＸＭＬからＣＳＶへ（今回は実装しない）
			case XML_TO_CSV:
				return false;
			//ＺＩＰ圧縮
			case ZIP:
				return encodeZip(fromFilePath, toFilePath);
			//ＺＩＰ解凍
			case UNZIP:
				return decodeZip(fromFilePath, toFilePath);

			default:
				return false;
		}
	}

	/**
	 * ZIP圧縮を行う
	 * @param fromFilePath
	 * @param toFilePath
	 * @return
	 */
	private static boolean encodeZip(String fromFilePath, String toFilePath) {
		String zipFileName = Zip.encodeZip(fromFilePath, toFilePath);
		if (zipFileName.equals(toFilePath)) {
		    return true;
		} else {
			return false;
		}
	}

// 20030512 @ADD yamanaka start
	/**
	 * ZIP圧縮を行う
	 * @param fromFileList ファイルリスト
	 * @param toFilePath
	 * @return
	 */
	public static boolean encodeZip(List fromFileList, String toFilePath) {
		Zip zip = new Zip();
		for (int i = 0 ; i < fromFileList.size() ; i++) {
		    zip.setSrcFilename((String)fromFileList.get(i));
		}
		return zip.encodeZip(toFilePath);
	}
// 20030512 @ADD yamanaka end

	/**
	 * ＺＩＰ解凍を行う
	 * @param fromFilePath
	 * @param toFilePath
	 * @return
	 */
	private static boolean decodeZip(String fromFilePath, String toFilePath) {
		return Zip.decodeZip(fromFilePath, toFilePath);
	}

    /**
     * ファイルをコピーします。
     * @param fromPath コピー元ファイルパス
     * @param toPath コピー先ファイルパス
     * @param append 上書き指定フラグ
     * @return
     */
    private static boolean copyFile(String fromFilePath, String toFilePath, boolean append) {
		InputStream  from_stream;  // 入力ファイルストリーム・オブジェクト
        OutputStream  to_stream;   // 出力ファイルストリーム・オブジェクト
        int rcount;        // 実際に読み込めたデータの大きさを保持
        byte buffer[];    // バッファ

		// バッファを作る
		buffer = new byte [16*1024]; //ｻｲｽﾞは適当

		from_stream = null;
		to_stream = null;

		try {
			// 複写元のファイルを開く
			from_stream = new FileInputStream(fromFilePath);
			// 複写先のファイルを開く
			to_stream = new FileOutputStream(toFilePath, append);
			// 複写を行なう
		    while ((rcount = from_stream.read(buffer)) >= 0){
				to_stream.write(buffer, 0, rcount);
		    }
			// 複写元のファイルを閉じる
			from_stream.close();
			// 複写先のファイルを閉じる
			to_stream.close();
		} catch (IOException e){    // 入出力例外(エラー)の捕捉
			try {
				if (from_stream != null)
					from_stream.close();
				if (to_stream != null)
					to_stream.close();
			} catch (IOException e2){
			}
			return false;
		}

		return true;
    }

	/**
     * 指定されたファイルを削除する
     * @param filePath 削除するファイルのパス
     * @return 削除された場合true、削除されなかった場合false
     */
	public static boolean deleteFile(String filePath) {
		File delFile;

		delFile = new File(filePath);

        try {
            if (!delFile.exists()) {
				return false;
			}
			return delFile.delete();
        } catch (Exception e) {
            return false;
        }
	}

	/**
	 * デバッグ用
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length > 2) {
			if (args[0].equals("ZIP")) { //圧縮
				List fileList = new ArrayList();
				for (int i = 1 ; i < args.length - 1 ; i++) {
					fileList.add(args[i]);
				}
				encodeZip(fileList, args[args.length - 1]);
				return;
			} else if (args[0].equals("UNZIP")) { //解凍
				DataExchanger.exchange(DataExchanger.UNZIP, args[1], args[2]);
				return;
			} else { //圧縮
				List fileList = new ArrayList();
				for (int i = 0 ; i < args.length - 1 ; i++) {
					fileList.add(args[i]);
				}
				encodeZip(fileList, args[args.length - 1]);
				return;
			}
		}

		//boolean ok = DataExchanger.exchange(DataExchanger.NO_EXCHANGE_MOVE, "C:/temp/Test/ThisIsTest.txt", "C:/temp/Test/C/aaa.txt");
		//System.out.println(ok);
		//DataExchanger.exchange(DataExchanger.ZIP, "/temp/Test/rbsite20020911_2.exe", "/temp/Test/C/rbsite20020911_2.zip");
		DataExchanger.exchange(DataExchanger.UNZIP, "C:/temp/Test/C/rbsite20020911_2.zip", "C:/temp/Test/C/");
	}
}