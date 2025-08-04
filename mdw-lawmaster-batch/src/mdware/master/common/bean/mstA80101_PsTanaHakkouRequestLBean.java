/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）PS／棚割リクエストTRのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するPS／棚割リクエストTRのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
package  mdware.master.common.bean;

import java.util.*;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）PS／棚割リクエストTRのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するPS／棚割リクエストTRのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author yaoyujun
 * @version 1.0(2006/07/12)初版作成
 */
public class mstA80101_PsTanaHakkouRequestLBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SEND_DT_MAX_LENGTH = 8;
	public static final int TENPO_CD_MAX_LENGTH = 6;
	public static final int TENPO_NM_MAX_LENGTH = 20;	
	public static final int UNIT_CD_MAX_LENGTH = 4;
	public static final int UNIT_NM_MAX_LENGTH = 20;
	public static final int SYOHIN_CD_MAX_LENGTH = 13;
	public static final int PS_SYOHIN_NA_MAX_LENGTH = 80;
	public static final int DAISI_NO_NB_MAX_LENGTH = 4;
//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	public static final int HINBAN_CD_MAX_LENGTH = 4;
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑

	private String send_dt = null;
	private String tenpo_cd = null;
	private String tenpo_nm = null;
	private String unit_cd = null;
	private String unit_nm = null;
	private String syohin_cd = null;
	private String ps_syohin_na = null;
	private String daisi_no_nb = null;
	private String ps_maisu_qt = null;
//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	private String hinban_cd = null;
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
	
	private String sentaku = "0";
	private String disbale = null;	
	private String updateUserId = null;
	private String bumon_cd = null;
	private String entry_kb = null;
	private String ps_request_kb = null;
	private String ps_sofusaki_kb = null;
	private String entry_kino_kb = null;	
	private String by_no = null;	
	private String kai_page_kb = null;
	private String pc_kb = null;
	private String comment_tx = null;
	private String tanawari_gai_kb = null;
	private String send_end_kb = null;
	private String daityo_kb = null;//台帳出力区分：DAITYO_KB
	private String irai_no = null; //依頼NO：IRAI_NO

	
	private String deleteFg = null;
	private String insertUserId = null;
	private String insertTs = null;
	private String updateTs = null;	
	private Map CtrlColor = new HashMap(); // コントロール背景色（ヘッダ）

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

	/**
	 * TestBeanを１件のみ抽出したい時に使用する
	 */
	public static mstA80101_PsTanaHakkouRequestLBean getTestBean(DataHolder dataHolder)
	{
		mstA80101_PsTanaHakkouRequestLBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstA80101_PsTanaHakkouRequestLDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstA80101_PsTanaHakkouRequestLBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// send_dtに対するセッターとゲッターの集合
	public boolean setSendDt(String send_dt)
	{
		this.send_dt = send_dt;
		return true;
	}
	public String getSendDt()
	{
		return cutString(this.send_dt,SEND_DT_MAX_LENGTH);
	}
	public String getSendDtString()
	{
		return "'" + cutString(this.send_dt,SEND_DT_MAX_LENGTH) + "'";
	}
	public String getSendDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_dt,SEND_DT_MAX_LENGTH));
	}


	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH));
	}
	
	// tenpo_nmに対するセッターとゲッターの集合
	public boolean setTenpoNm(String tenpo_nm)
	{
		this.tenpo_nm = tenpo_nm;
		return true;
	}
	public String getTenpoNm()
	{
		return cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH);
	}
	public String getTenpoNmString()
	{
		return "'" + cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH) + "'";
	}
	public String getTenpoNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH));
	}


	// unit_cdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unit_cd)
	{
		this.unit_cd = unit_cd;
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unit_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unit_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}

//	↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
	// hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH);
	}
	public String getHinbanCdString()
	{
		return "'" + cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinban_cd,UNIT_CD_MAX_LENGTH));
	}
//	↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑

	// unit_nmに対するセッターとゲッターの集合
	public boolean setUnitNm(String unit_nm)
	{
		this.unit_nm = unit_nm;
		return true;
	}
	public String getUnitNm()
	{
		return cutString(this.unit_nm,UNIT_NM_MAX_LENGTH);
	}
	public String getUnitNmString()
	{
		return "'" + cutString(this.unit_nm,UNIT_NM_MAX_LENGTH) + "'";
	}
	public String getUnitNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_nm,UNIT_NM_MAX_LENGTH));
	}	


	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}


	// ps_syohin_naに対するセッターとゲッターの集合
	public boolean setPsSyohinNa(String ps_syohin_na)
	{
		this.ps_syohin_na = ps_syohin_na;
		return true;
	}
	public String getPsSyohinNa()
	{
		return cutString(this.ps_syohin_na,PS_SYOHIN_NA_MAX_LENGTH);
	}
	public String getPsSyohinNaString()
	{
		return "'" + cutString(this.ps_syohin_na,PS_SYOHIN_NA_MAX_LENGTH) + "'";
	}
	public String getPsSyohinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ps_syohin_na,PS_SYOHIN_NA_MAX_LENGTH));
	}


	// daisi_no_nbに対するセッターとゲッターの集合
	public boolean setDaisiNoNb(String daisi_no_nb)
	{
		this.daisi_no_nb = daisi_no_nb;
		return true;
	}
	public String getDaisiNoNb()
	{
		return cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH);
	}
	public String getDaisiNoNbString()
	{
		return "'" + cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH) + "'";
	}
	public String getDaisiNoNbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.daisi_no_nb,DAISI_NO_NB_MAX_LENGTH));
	}

	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor(Map);<br>
	 * <br>
	 * 
	 * @param コントロールカラーMap
	 */
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}

	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor(); 戻り値 文字列<br>
	 * <br>
	 * 
	 * @return コントロールカラーMap
	 */
	public Map getCtrlColor() {
		return this.CtrlColor;
	}		

	// ps_maisu_qtに対するセッターとゲッターの集合
	public boolean setPsMaisuQt(String ps_maisu_qt)
	{
		try
		{
			this.ps_maisu_qt = ps_maisu_qt;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}	
	public String getPsMaisuQt()
	{
		return this.ps_maisu_qt;
	}
	public String getPsMaisuQtString()
	{
		return HTMLStringUtil.convert(this.ps_maisu_qt);
	}
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  send_dt = " + getSendDtString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_nm = " + getUnitNmString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  ps_syohin_na = " + getPsSyohinNaString()
			+ "  daisi_no_nb = " + getDaisiNoNbString()
			+ "  ps_maisu_qt = " + getPsMaisuQtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mstA80101_PsTanaHakkouRequestLBean コピー後のクラス
	 */
	public mstA80101_PsTanaHakkouRequestLBean createClone()
	{
		mstA80101_PsTanaHakkouRequestLBean bean = new mstA80101_PsTanaHakkouRequestLBean();
		bean.setSendDt(this.send_dt);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNm(this.tenpo_nm);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNm(this.unit_nm);
		bean.setSyohinCd(this.syohin_cd);
		bean.setPsSyohinNa(this.ps_syohin_na);
		bean.setDaisiNoNb(this.daisi_no_nb);
		bean.setPsMaisuQt(this.ps_maisu_qt);
//		↓↓2006.10.13 H.Yamamoto カスタマイズ修正↓↓
		bean.setHinbanCd(this.hinban_cd);
//		↑↑2006.10.13 H.Yamamoto カスタマイズ修正↑↑
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mstA80101_PsTanaHakkouRequestLBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
	public String getSentaku() {
		return sentaku;
	}
	public void setSentaku(String sentaku) {
		this.sentaku = sentaku;
	}
	public String getDisbale() {
		return disbale;
	}
	public void setDisbale(String disbale) {
		this.disbale = disbale;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getBumon_cd() {
		return bumon_cd;
	}
	public void setBumon_cd(String bumon_cd) {
		this.bumon_cd = bumon_cd;
	}
	public String getEntry_kb() {
		return entry_kb;
	}
	public void setEntry_kb(String entry_kb) {
		this.entry_kb = entry_kb;
	}
	public String getPs_request_kb() {
		return ps_request_kb;
	}
	public void setPs_request_kb(String ps_request_kb) {
		this.ps_request_kb = ps_request_kb;
	}
	public String getEntry_kino_kb() {
		return entry_kino_kb;
	}
	public void setEntry_kino_kb(String entry_kino_kb) {
		this.entry_kino_kb = entry_kino_kb;
	}
	public String getPs_sofusaki_kb() {
		return ps_sofusaki_kb;
	}
	public void setPs_sofusaki_kb(String ps_sofusaki_kb) {
		this.ps_sofusaki_kb = ps_sofusaki_kb;
	}
	public String getBy_no() {
		return by_no;
	}
	public void setBy_no(String by_no) {
		this.by_no = by_no;
	}
	public String getKai_page_kb() {
		return kai_page_kb;
	}
	public void setKai_page_kb(String kai_page_kb) {
		this.kai_page_kb = kai_page_kb;
	}
	public String getPc_kb() {
		return pc_kb;
	}
	public void setPc_kb(String pc_kb) {
		this.pc_kb = pc_kb;
	}
	public String getComment_tx() {
		return comment_tx;
	}
	public void setComment_tx(String comment_tx) {
		this.comment_tx = comment_tx;
	}
	public String getTanawari_gai_kb() {
		return tanawari_gai_kb;
	}
	public void setTanawari_gai_kb(String tanawari_gai_kb) {
		this.tanawari_gai_kb = tanawari_gai_kb;
	}
	public String getSend_end_kb() {
		return send_end_kb;
	}
	public void setSend_end_kb(String send_end_kb) {
		this.send_end_kb = send_end_kb;
	}
	public String getDeleteFg() {
		return deleteFg;
	}
	public void setDeleteFg(String deleteFg) {
		this.deleteFg = deleteFg;
	}
	public String getInsertTs() {
		return insertTs;
	}
	public void setInsertTs(String insertTs) {
		this.insertTs = insertTs;
	}
	public String getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(String insertUserId) {
		this.insertUserId = insertUserId;
	}
	public String getUpdateTs() {
		return updateTs;
	}
	public void setUpdateTs(String updateTs) {
		this.updateTs = updateTs;
	}
	public String getDaityo_kb() {
		return daityo_kb;
	}
	public void setDaityo_kb(String daityo_kb) {
		this.daityo_kb = daityo_kb;
	}
	public String getIrai_no() {
		return irai_no;
	}
	public void setIrai_no(String irai_no) {
		this.irai_no = irai_no;
	}
}
