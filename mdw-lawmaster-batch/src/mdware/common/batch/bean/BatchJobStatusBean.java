package mdware.common.batch.bean;

/**
 * <p>タイトル: BATCHJOBST行データ保持用BEAN</p>
 * <p>説明: BATCHJOBSTテーブルの行データを保持するためのBEAN</p>
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
public class BatchJobStatusBean {

	//BATCHJOBSTテーブルのカラム定義
	private String jobID       = null;
	private String syoriDt     = null;
	private String insertTsForWhere     = null;
	private String insertTsForUpdate    = null;
	private int sts         	= 0;
	private String startTs     = null;
	private String endTs       = null;
	private String className   = null;
	private String classNameJa = null;
	private String subSysNa    = null;
	private int inpCnt1 = 0;
	private int inpCnt2 = 0;
	private int inpCnt3 = 0;
	private int inpCnt4 = 0;
	private int inpCnt5 = 0;
	private int outCnt1 = 0;
	private int outCnt2 = 0;
	private int outCnt3 = 0;
	private int outCnt4 = 0;
	private int outCnt5 = 0;
	private boolean isStartStatus = true; //true：開始ステータス？false：終了ステータス？

	/**
	 * コンストラクタ
	 */
	public BatchJobStatusBean() {

		this.jobID       = "";
		this.syoriDt     = "";
		this.insertTsForWhere  = "";
		this.insertTsForUpdate = "";
		this.sts         = 0;
		this.startTs     = "";
		this.endTs       = "";
		this.className   = "";
		this.classNameJa = "";
		this.subSysNa    = "";
		this.inpCnt1 = 0;
		this.inpCnt2 = 0;
		this.inpCnt3 = 0;
		this.inpCnt4 = 0;
		this.inpCnt5 = 0;
		this.outCnt1 = 0;
		this.outCnt2 = 0;
		this.outCnt3 = 0;
		this.outCnt4 = 0;
		this.outCnt5 = 0;
		this.isStartStatus = true;
	}

	/**
	 * @return className を戻します。
	 */
	public String getClassName() {
		return (className == null) ? "" : this.className;
	}

	/**
	 * @param className className を設定。
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return classNameJa を戻します。
	 */
	public String getClassNameJa() {
		return (classNameJa == null) ? "" : this.classNameJa;
	}

	/**
	 * @param classNameJa classNameJa を設定。
	 */
	public void setClassNameJa(String classNameJa) {
		this.classNameJa = classNameJa;
	}

	/**
	 * @return endTs を戻します。
	 */
	public String getEndTs() {
		return (endTs == null) ? "" : this.endTs;
	}

	/**
	 * @param endTs endTs を設定。
	 */
	public void setEndTs(String endTs) {
		this.endTs = endTs;
	}

	/**
	 * @return inpCnt1 を戻します。
	 */
	public int getInpCnt1() {
		return this.inpCnt1;
	}

	/**
	 * @param inpCnt1 inpCnt1 を設定。
	 */
	public void setInpCnt1(int inpCnt1) {
		this.inpCnt1 = inpCnt1;
	}

	/**
	 * @return inpCnt2 を戻します。
	 */
	public int getInpCnt2() {
		return this.inpCnt2;
	}

	/**
	 * @param inpCnt2 inpCnt2 を設定。
	 */
	public void setInpCnt2(int inpCnt2) {
		this.inpCnt2 = inpCnt2;
	}

	/**
	 * @return inpCnt3 を戻します。
	 */
	public int getInpCnt3() {
		return this.inpCnt3;
	}

	/**
	 * @param inpCnt3 inpCnt3 を設定。
	 */
	public void setInpCnt3(int inpCnt3) {
		this.inpCnt3 = inpCnt3;
	}

	/**
	 * @return inpCnt4 を戻します。
	 */
	public int getInpCnt4() {
		return this.inpCnt4;
	}

	/**
	 * @param inpCnt4 inpCnt4 を設定。
	 */
	public void setInpCnt4(int inpCnt4) {
		this.inpCnt4 = inpCnt4;
	}

	/**
	 * @return inpCnt5 を戻します。
	 */
	public int getInpCnt5() {
		return this.inpCnt5;
	}

	/**
	 * @param inpCnt5 inpCnt5 を設定。
	 */
	public void setInpCnt5(int inpCnt5) {
		this.inpCnt5 = inpCnt5;
	}

	/**
	 * @return insertTs を戻します。
	 */
	public String getInsertTsForWhere() {
		return (insertTsForWhere == null) ? "" : this.insertTsForWhere;
	}

	/**
	 * @param insertTs insertTs を設定。
	 */
	public void setInsertTsForWhere(String insertTs) {
		this.insertTsForWhere = insertTs;
	}

	/**
	 * @return insertTsForUpdate を戻します。
	 */
	public String getInsertTsForUpdate() {
		return (insertTsForUpdate == null) ? "" : this.insertTsForUpdate;
	}

	/**
	 * @param insertTsForUpdate insertTsForUpdate を設定。
	 */
	public void setInsertTsForUpdate(String insertTsForUpdate) {
		this.insertTsForUpdate = insertTsForUpdate;
	}

	/**
	 * @return jobID を戻します。
	 */
	public String getJobID() {
		return (jobID == null) ? "" : this.jobID;
	}

	/**
	 * @param jobID jobID を設定。
	 */
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	/**
	 * @return outCnt1 を戻します。
	 */
	public int getOutCnt1() {
		return this.outCnt1;
	}

	/**
	 * @param outCnt1 outCnt1 を設定。
	 */
	public void setOutCnt1(int outCnt1) {
		this.outCnt1 = outCnt1;
	}

	/**
	 * @return outCnt2 を戻します。
	 */
	public int getOutCnt2() {
		return this.outCnt2;
	}

	/**
	 * @param outCnt2 outCnt2 を設定。
	 */
	public void setOutCnt2(int outCnt2) {
		this.outCnt2 = outCnt2;
	}

	/**
	 * @return outCnt3 を戻します。
	 */
	public int getOutCnt3() {
		return this.outCnt3;
	}

	/**
	 * @param outCnt3 outCnt3 を設定。
	 */
	public void setOutCnt3(int outCnt3) {
		this.outCnt3 = outCnt3;
	}

	/**
	 * @return outCnt4 を戻します。
	 */
	public int getOutCnt4() {
		return this.outCnt4;
	}

	/**
	 * @param outCnt4 outCnt4 を設定。
	 */
	public void setOutCnt4(int outCnt4) {
		this.outCnt4 = outCnt4;
	}

	/**
	 * @return outCnt5 を戻します。
	 */
	public int getOutCnt5() {
		return this.outCnt5;
	}

	/**
	 * @param outCnt5 outCnt5 を設定。
	 */
	public void setOutCnt5(int outCnt5) {
		this.outCnt5 = outCnt5;
	}

	/**
	 * @return startTs を戻します。
	 */
	public String getStartTs() {
		return (startTs == null) ? "" : this.startTs;
	}

	/**
	 * @param startTs startTs を設定。
	 */
	public void setStartTs(String startTs) {
		this.startTs = startTs;
	}

	/**
	 * @return sts を戻します。
	 */
	public String getSts() {
		return Integer.toString(this.sts);
	}

	/**
	 * @param sts sts を設定。
	 */
	public void setSts(int iSts) {
		this.sts = iSts;
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
	 * @return syoriDt を戻します。
	 */
	public String getSyoriDt() {
		return (syoriDt == null) ? "" : this.syoriDt;
	}

	/**
	 * @param syoriDt syoriDt を設定。
	 */
	public void setSyoriDt(String syoriDt) {
		this.syoriDt = syoriDt;
	}

	/**
	 * @return isStartStatus を戻します。
	 */
	public boolean isStartStatus() {
		return isStartStatus;
	}

	/**
	 * @param isStartStatus isStartStatus を設定。
	 */
	public void setStartStatus(boolean isStartStatus) {
		this.isStartStatus = isStartStatus;
	}
	
}

