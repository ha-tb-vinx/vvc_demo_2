package mdware.common.batch.util.file;

import java.io.*;

/**
 * <p>タイトル: PrefixSuffixSearchFileFilter</p>
 * <p>説明: 指定の接頭辞、接尾辞を持つファイル用フィルタ</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yamanaka
 * @version 1.0
 */

public class PrefixSuffixSearchFileFilter implements FilenameFilter {
	private String prefix = null;
	private String suffix = null;

	/**
	 * コンストラクタ
	 * @param prefix 接頭辞
	 * @param suffix 接尾辞
	 */
	public PrefixSuffixSearchFileFilter(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	/**
	 * コンストラクタで指定された接頭辞、接尾辞を持つファイル名の場合にtrueを返します。
	 * @param dir
	 * @param name
	 * @return
	 */
	public boolean accept(File dir, String name) {
		if (prefix == null || prefix.trim().length() == 0) {
			//if (name.endsWith(this.suffix)) {
			if (name.toLowerCase().endsWith(this.suffix.toLowerCase())) { //20030221 @UPD yamanaka
				return true;
			}
		}

		if (suffix == null || suffix.trim().length() == 0) {
			//if (name.startsWith(this.prefix)) {
			if (name.toLowerCase().startsWith(this.prefix.toLowerCase())) { //20030221 @UPD yamanaka
				return true;
			}
		}

		//if (name.startsWith(this.prefix) && name.endsWith(this.suffix)) {
		if (name.toLowerCase().startsWith(this.prefix.toLowerCase()) && name.toLowerCase().endsWith(this.suffix.toLowerCase())) { //20030221 @UPD yamanaka
			return true;
		}

		return false;
	}
}
