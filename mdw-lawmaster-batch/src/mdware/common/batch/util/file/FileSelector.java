package mdware.common.batch.util.file;

import java.io.*;

/**
 * <p>タイトル: FileSelector</p>
 * <p>説明: ディレクトリから指定されたファイル名を探します。</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.00
 * @version 1.00 2004.01.14 拡張子が無い場合でもOKのように修正
 */

public class FileSelector {

	/**
	 * 指定の接頭辞・接尾辞を持つファイルリストを返します。
	 * @param dir
	 * @param prifix
	 * @param suffix
	 * @return
	 */
	public static File[] getFileListWithPrifixSuffix(String dir, String prefix, String suffix) {
		PrefixSuffixSearchFileFilter fileFilter = new PrefixSuffixSearchFileFilter(prefix, suffix);
		File[] files = new File(dir).listFiles(fileFilter);

		if (files == null) {
			return new File[0];
		}

		return files;
	}

	/**
	 * 指定のファイルと同名で拡張子が異なるファイルが存在するかどうかのチェックを行います。
	 * @param dir
	 * @param fileName
	 * @param anotherSuffix
	 * @return true：存在する場合 false：存在しない場合
	 */
	public static boolean hasFileWithAnotherSuffix(String dir, String fileName, String anotherSuffix) {
		String fileNameWithoutSuffix = getFileNameWithoutSuffix(fileName);
		String[] fileNames = new File(dir).list(new PrefixSuffixSearchFileFilter(fileNameWithoutSuffix, anotherSuffix));
		for (int i = 0 ; i < fileNames.length ; i++) {
		    String compareName = fileNameWithoutSuffix;
			if (anotherSuffix.charAt(0) == '.') {
				compareName += anotherSuffix;
			} else {
				compareName += "." + anotherSuffix;
			}
			//if (fileNames[i].equals(compareName)) {
			if (fileNames[i].equalsIgnoreCase(compareName)) { //20030221 @UPD yamanaka
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定のファイル名より拡張子を除去したものを返します。
	 * @param fileName
	 * @return
	 */
	public static String getFileNameWithoutSuffix(String fileName) {
		int pos = fileName.lastIndexOf('.');
//2004.01.14 @ADD start
		if (pos < 0) {
			return fileName;
		}
//2004.01.14 @ADD end
		return fileName.substring(0, pos);
	}

	/**
	 * 指定のファイル名より拡張子を返します。
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		int pos = fileName.lastIndexOf('.');
//2004.01.14 @ADD start
		if (pos < 0) {
			return "";
		}
//2004.01.14 @ADD end
		return fileName.substring(pos + 1);
	}

	/**
	 * 指定のディレクトリに同名ファイルがあるかを調べ、存在した場合は枝番を振ったものを返します。
	 * aaaa.txt → aaaa_2.txtというように命名します。
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public synchronized static String getBranchFileName(String dir, String fileName) {
		File folder = new File(dir);
		if (!folder.exists()) { //フォルダが無ければ無条件に返す
			return fileName;
		} else {
			String suffix = getSuffix(fileName);
			String fileNameWithoutSuffix = getFileNameWithoutSuffix(fileName);
			int seqNo = 1;
			String branch = "";
			while (true) {
				File newFile = null;
				if (seqNo == 1) {
					newFile = new File(dir + "/" + fileName);
				} else {
					newFile = new File(dir + "/" + fileNameWithoutSuffix + "_" + seqNo + "." + suffix);
				}
				if (newFile.exists()) { //枝番を振る必要あり
					seqNo++;
				} else {
					return newFile.getName();
				}
			}
		}
	}

	public static void main(String[] args) {
		//File[] f = getFileListWithPrifixSuffix("c:/rbdata/ist/distribute", "ORD_", "dat");
		String filename = "ORD_20021216120001";
		System.out.println(getFileNameWithoutSuffix(filename));
		System.out.println(getSuffix(filename));
		System.out.println(getBranchFileName("c:/rbdata/ist/distribute/", "ORD_20021216120001.dat"));
	}
}