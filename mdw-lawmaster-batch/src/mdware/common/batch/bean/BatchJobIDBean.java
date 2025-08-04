package mdware.common.batch.bean;

/**
 * <p>タイトル: BATCHJOBID行データ保持用BEAN</p>
 * <p>説明: BATCHJOBIDテーブルの行データを保持するためのBEAN</p>
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
public class BatchJobIDBean {

	//
	private String jobId    = null;
	private String torokuKb = null;
	private String claSS    = null;
	private String classNa  = null;
	private String subSysNa = null;

	public BatchJobIDBean(){
		jobId    = "";
		torokuKb = "";
		claSS    = "";
		classNa  = "";
		subSysNa = "";
	}
	
	/**
	 * @return claSS を戻します。
	 */
	public String getClassName() {
		return (claSS == null) ? "" : this.claSS;
	}
	/**
	 * @param claSS claSS を設定。
	 */
	public void setClassName(String claSS) {
		this.claSS = claSS;
	}
	/**
	 * @return classNa を戻します。
	 */
	public String getClassNameJa() {
		return (classNa == null) ? "" : this.classNa;
	}
	/**
	 * @param classNa classNa を設定。
	 */
	public void setClassNameJa(String classNa) {
		this.classNa = classNa;
	}
	/**
	 * @return jobId を戻します。
	 */
	public String getJobId() {
		return (jobId == null) ? "" : this.jobId;
	}
	/**
	 * @param jobId jobId を設定。
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return subSysNa を戻します。
	 */
	public String getSubSysNa() {
		return (subSysNa == null) ? "" : this.subSysNa;
	}
	/**
	 * @param subSysNa subSysNa を設定。
	 */
	public void setSubSysNa(String subSysNa) {
		this.subSysNa = subSysNa;
	}
	/**
	 * @return torokuKb を戻します。
	 */
	public String getTorokuKb() {
		return (torokuKb == null) ? "" : this.torokuKb;
	}
	/**
	 * @param torokuKb torokuKb を設定。
	 */
	public void setTorokuKb(String torokuKb) {
		this.torokuKb = torokuKb;
	}
}
