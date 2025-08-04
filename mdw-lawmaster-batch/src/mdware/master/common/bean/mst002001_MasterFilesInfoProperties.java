package mdware.master.common.bean;


/**
 * <p>タイトル: </p>
 * <p>説明: マスター管理用ファイル情報など</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author H.Yamamoto
 * @version 1.0
 */

public class mst002001_MasterFilesInfoProperties{
	private String key = "";
	private String filesPath = "";
	private String info = "";
	/**
	 * コンストラクタ。
	 * ここでのみ情報やメッセージがセットできる。
	 * @param key 検索キー
	 * @param filesPath ファイルパス
	 * @param info 情報やメッセージ
	 */
	public mst002001_MasterFilesInfoProperties(String key, String filesPath, String info) {
		this.key = key.trim();
		this.filesPath = filesPath.trim();
		this.info = info.trim();
	}
	/**
	 * 検索キーを返す
	 * @return String 検索キー
	 */
	public String getKey() {
		return key;
	}

	/**
	 * ファイルパスを返す
	 * @return String
	 */
	public String getFilesPath() {
		return filesPath;
	}

	/**
	 * 情報やメッセージを返す。
	 * @return String URL
	 */
	public String getInfo() {
		return info;
	}
}
