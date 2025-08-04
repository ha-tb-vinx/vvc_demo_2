package mdware.common.batch.bean;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * <pre>
 * 入力
 *
 * 出力
 *
 * 戻り値
 *
 * </pre>
 *
 * @author k_tanigawa
 * @version 1.0 (2006/08/17) 初版作成
 */
public class BatchJobDateBean {

	private String batchDate  = null;	//バッチ日付
	private String createDate = null;	//作成年月日
	/**
	 * @return batchDate を戻します。
	 */
	public String getBatchDate() {
		return batchDate;
	}
	/**
	 * @param batchDate batchDate を設定。
	 */
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	/**
	 * @return createDate を戻します。
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate createDate を設定。
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
