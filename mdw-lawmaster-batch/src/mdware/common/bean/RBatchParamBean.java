package mdware.common.bean;

/**
 * <p>タイトル:R_BATCH_PARAM テーブル内容格納用Bean</p>
 * <p>説明:R_BATCH_PARAM テーブル内容格納用Bean</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00<BR>
 * @author T.Yokoyama
 */
public class RBatchParamBean {
	private String batch_id;	// batch_id
	private String sub_id;		// SubId
	private String parame1_tx;	// parame1_tx
	private String parame2_tx;	// parame2_tx
	private String parame3_tx;	// parame3_tx
	/**
	 * batch_idを返します
	 * @return batch_id
	 */
	public String getBatch_id() {
		return batch_id;
	}
	/**
	 * batch_idを設定します
	 * @param batch_id
	 */
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	/**
	 * parame1_txを返します
	 * @return parame1_tx
	 */
	public String getParame1_tx() {
		return parame1_tx;
	}
	/**
	 * parame1_txを設定します
	 * @param parame1_tx
	 */
	public void setParame1_tx(String parame1_tx) {
		this.parame1_tx = parame1_tx;
	}
	/**
	 * parame2_txを返します
	 * @return parame2_tx
	 */
	public String getParame2_tx() {
		return parame2_tx;
	}
	/**
	 * parame2_txを設定します
	 * @param parame2_tx
	 */
	public void setParame2_tx(String parame2_tx) {
		this.parame2_tx = parame2_tx;
	}
	/**
	 * parame3_txを返します
	 * @return parame3_tx
	 */
	public String getParame3_tx() {
		return parame3_tx;
	}
	/**
	 * parame3_txを設定します
	 * @param parame3_tx
	 */
	public void setParame3_tx(String parame3_tx) {
		this.parame3_tx = parame3_tx;
	}
	/**
	 * sub_idを返します
	 * @return sub_id
	 */
	public String getSub_id() {
		return sub_id;
	}
	/**
	 * sub_idを設定します
	 * @param sub_id
	 */
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

}
