package mdware.master.common.bean;

import java.util.Iterator;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author 未入力
 * @version 1.0
 */
public class mstA90101_PsTanpinHakkouRequestBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SEND_DT_MAX_LENGTH = 8;// 送信日
	public static final int TENPO_CD_MAX_LENGTH = 6;// 店舗コード
	public static final int TENPO_NM_MAX_LENGTH = 20;// 店舗名称
	public static final int UNIT_CD_MAX_LENGTH = 4;// ユニットコード
	public static final int UNIT_NM_MAX_LENGTH = 20;// ユニット名称
	public static final int SYOHIN_CD_MAX_LENGTH = 13;// 商品コード
	public static final int PS_SYOHIN_NA_MAX_LENGTH = 80;// PS商品名
	public static final int DAISI_NO_NB_MAX_LENGTH = 2;// 台紙NO
	public static final int BUMON_CD_MAX_LENGTH= 4; //部門コードの長さ
	public static final int SYOKAI_USER_ID_MAX_LENGTH = 20;//初回登録者IDの長さ
	public static final int PC_KB_MAX_LENGTH = 1;//PC発行区分の長さ
	public static final int ENTRY_KB_MAX_LENGTH = 1;// 登録区分
	public static final int PS_REQUEST_KB_MAX_LENGTH = 1;// PSリクエスト区分
	public static final int PS_SOFUSAKI_KB_MAX_LENGTH = 1;// PS送付先区分
	public static final int ENTRY_KINO_KB_MAX_LENGTH = 1;// 登録機能区分
	public static final int KAI_PAGE_KB_MAX_LENGTH = 1;// 改ページ区分
	public static final int BY_NO_MAX_LENGTH = 20;// バイヤーNO
	public static final int COMMENT_TX_MAX_LENGTH = 80;// コメント
	public static final int TANAWARI_GAI_KB_MAX_LENGTH = 1;// 棚割外区分
	public static final int SEND_END_KB_MAX_LENGTH = 1;// 送信済区分
	public static final int DELETE_FG_MAX_LENGTH = 1;// 削除フラグ
	public static final int INSERT_TS_MAX_LENGTH = 14;// 作成年月日
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;// 作成者ID
	public static final int UPDATE_TS_MAX_LENGTH = 14;// 更新年月日
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;// 更新者ID
	public static final int HINBAN_CD_MAX_LENGTH = 4;// 品番コード
	public static final int DAITYO_KB_MAX_LENGTH = 1;// 台帳出力区分
	public static final int IRAI_NO_MAX_LENGTH = 6;// 依頼NO
	
	private String send_dt = null;// 送信日
	private String tenpo_cd = null;;// 店舗コード
	private String tenpo_nm = null;// 店舗名称
	private String unit_cd = null;	// ユニットコード
	private String unit_nm = null;// ユニット名称
	private String syohin_cd = null;// 商品コード
	private String ps_syohin_na = null;// PS商品名
	private String daisi_no_nb = null;// 台紙NO
	private String ps_maisu_qt = null;// PS枚数
	private String bumon_cd             = null;	//部門コード
	private String syokai_user_id = null;	//初回登録者ID
	private String pc_kb = null;	//PC発行区分
	private String entry_kb = null;// 登録区分
	private String ps_request_kb = null;;// PSリクエスト区分
	private String ps_sofusaki_kb = null;// PS送付先区分
	private String entry_kino_kb = null;	// 登録機能区分
	private String kai_page_kb = null;// 改ページ区分
	private String by_no = null;// バイヤーNO
	private String comment_tx = null;// コメント
	private String tanawari_gai_kb = null;// 棚割外区分
	private String  send_end_kb =null;// 送信済区分
	private String delete_fg         = null;	//削除フラグ
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者ID
	private String hinban_cd = null;	//品番コード
	private String daityo_kb = null;		// 台帳出力区分
	private String irai_no = null;			// 依頼NO
	
	private Map    ctlColor 			= null;	//画面コントロール色
	private String disbale 			= null;	//明細入力可否
	private String sentaku 			= "";		//画面入力CheckBox

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
	 * mstA90101_PsTanpinHakkouRequestBeanを１件のみ抽出したい時に使用する
	 */
	public static mstA90101_PsTanpinHakkouRequestBean getTestBean(DataHolder dataHolder)
	{
		mstA90101_PsTanpinHakkouRequestBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mstA90101_PsTanpinHakkouRequestDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mstA90101_PsTanpinHakkouRequestBean )ite.next();
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


	// kanji_rn_tenpoに対するセッターとゲッターの集合
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


	// kanji_rn_unitに対するセッターとゲッターの集合
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

	// ps_maisu_qtに対するセッターとゲッターの集合
	public boolean setPsMaisuQt(String ps_maisu_qt)
	{

		this.ps_maisu_qt = ps_maisu_qt;
		return true;
	}

	public String getPsMaisuQtString()
	{
		return this.ps_maisu_qt;
	}
	
	public boolean setBumonCd(String bumon_cd)
	{
		this.bumon_cd = bumon_cd;
		return true;
	}
	public String getBumonCd()
	{
		return cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH);
	}
	public String getBumonCdString()
	{
		return "'" + cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH) + "'";
	}
	public String getBumonCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.bumon_cd,BUMON_CD_MAX_LENGTH));
	}
	public boolean setSyokaiUserId(String syokai_user_id)
	{
		this.syokai_user_id = syokai_user_id;
		return true;
	}
	public String getSyokaiUserId()
	{
		return cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH);
	}
	public String getSyokaiUserIdString()
	{
		return "'" + cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH) + "'";
	}
	public String getSyokaiUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syokai_user_id,SYOKAI_USER_ID_MAX_LENGTH));
	}

	// entry_kbに対するセッターとゲッターの集合
	public boolean setEntryKb(String entry_kb)
	{
		this.entry_kb = entry_kb;
		return true;
	}
	public String getEntryKb()
	{
		return cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH);
	}
	public String getEntryKbString()
	{
		return "'" + cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH) + "'";
	}
	public String getEntryKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.entry_kb,ENTRY_KB_MAX_LENGTH));
	}

	// ps_request_kbに対するセッターとゲッターの集合
	public boolean setPsRequestKb(String ps_request_kb)
	{
		this.ps_request_kb = ps_request_kb;
		return true;
	}
	public String getPsRequestKb()
	{
		return cutString(this.ps_request_kb,PS_REQUEST_KB_MAX_LENGTH);
	}
	public String getPsRequestKbString()
	{
		return "'" + cutString(this.ps_request_kb,PS_REQUEST_KB_MAX_LENGTH) + "'";
	}
	public String getPsRequestKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ps_request_kb,PS_REQUEST_KB_MAX_LENGTH));
	}

	// ps_sofusaki_kbに対するセッターとゲッターの集合
	public boolean setPsSofusakiKb(String ps_sofusaki_kb)
	{
		this.ps_sofusaki_kb = ps_sofusaki_kb;
		return true;
	}
	public String getPsSofusakiKb()
	{
		return cutString(this.ps_sofusaki_kb,PS_SOFUSAKI_KB_MAX_LENGTH);
	}
	public String getPsSofusakiKbString()
	{
		return "'" + cutString(this.ps_sofusaki_kb,PS_SOFUSAKI_KB_MAX_LENGTH) + "'";
	}
	public String getPsSofusakiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ps_sofusaki_kb,PS_SOFUSAKI_KB_MAX_LENGTH));
	}

	// entry_kino_kbに対するセッターとゲッターの集合
	public boolean setEntryKinoKb(String entry_kino_kb)
	{
		this.entry_kino_kb = entry_kino_kb;
		return true;
	}
	public String getEntryKinoKb()
	{
		return cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH);
	}
	public String getEntryKinoKbString()
	{
		return "'" + cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH) + "'";
	}
	public String getEntryKinoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.entry_kino_kb,ENTRY_KINO_KB_MAX_LENGTH));
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

	// kai_page_kbに対するセッターとゲッターの集合
	public boolean setKaiPageKb(String kai_page_kb)
	{
		this.kai_page_kb = kai_page_kb;
		return true;
	}
	public String getKaiPageKb()
	{
		return cutString(this.kai_page_kb,KAI_PAGE_KB_MAX_LENGTH);
	}
	public String getKaiPageKbString()
	{
		return "'" + cutString(this.kai_page_kb,KAI_PAGE_KB_MAX_LENGTH) + "'";
	}
	public String getKaiPageKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kai_page_kb,KAI_PAGE_KB_MAX_LENGTH));
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

	// by_noに対するセッターとゲッターの集合
	public boolean setByNo(String by_no)
	{
		this.by_no = by_no;
		return true;
	}
	public String getByNo()
	{
		return cutString(this.by_no,BY_NO_MAX_LENGTH);
	}
	public String getByNoString()
	{
		return "'" + cutString(this.by_no,BY_NO_MAX_LENGTH) + "'";
	}
	public String getByNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.by_no,BY_NO_MAX_LENGTH));
	}

	// comment_txに対するセッターとゲッターの集合
	public boolean setCommentTx(String comment_tx)
	{
		this.comment_tx = comment_tx;
		return true;
	}
	public String getCommentTx()
	{
		return cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH);
	}
	public String getCommentTxString()
	{
		return "'" + cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH) + "'";
	}
	public String getCommentTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_tx,COMMENT_TX_MAX_LENGTH));
	}

	// tanawari_gai_kbに対するセッターとゲッターの集合
	public boolean setTanawariGaiKb(String tanawari_gai_kb)
	{
		this.tanawari_gai_kb = tanawari_gai_kb;
		return true;
	}
	public String getTanawariGaiKb()
	{
		return cutString(this.tanawari_gai_kb,TANAWARI_GAI_KB_MAX_LENGTH);
	}
	public String getTanawariGaiKbString()
	{
		return "'" + cutString(this.tanawari_gai_kb,TANAWARI_GAI_KB_MAX_LENGTH) + "'";
	}
	public String getTanawariGaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanawari_gai_kb,TANAWARI_GAI_KB_MAX_LENGTH));
	}

	// send_end_kbに対するセッターとゲッターの集合
	public boolean setSendEndKb(String send_end_kb)
	{
		this.send_end_kb = send_end_kb;
		return true;
	}
	public String getSendEndKb()
	{
		return cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH);
	}
	public String getSendEndKbString()
	{
		return "'" + cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH) + "'";
	}
	public String getSendEndKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.send_end_kb,SEND_END_KB_MAX_LENGTH));
	}

	// delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
		return true;
	}
	public String getDeleteFg()
	{
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString()
	{
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}

	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId()
	{
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString()
	{
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}


	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}


	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}

	// pc_kbに対するセッターとゲッターの集合
	public boolean setPcKb(String pc_kb)
	{
		this.pc_kb = pc_kb;
		return true;
	}
	public String getPcKb()
	{
		return cutString(this.pc_kb,PC_KB_MAX_LENGTH);
	}
	public String getPcKbString()
	{
		return "'" + cutString(this.pc_kb,PC_KB_MAX_LENGTH) + "'";
	}
	public String getPcKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_kb,PC_KB_MAX_LENGTH));
	}

	// hinban_cdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinban_cd)
	{
		this.hinban_cd = hinban_cd;
		return true;
	}
	public String getHinbanCd()
	{
		return cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH);
	}
	public String getHinbanCdString()
	{
		return "'" + cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinban_cd,HINBAN_CD_MAX_LENGTH));
	}

	// entry_kino_kbに対するセッターとゲッターの集合
	public boolean setDaityoKb(String daityo_kb)
	{
		this.daityo_kb = daityo_kb;
		return true;
	}
	public String getDaityoKb()
	{
		return cutString(this.daityo_kb,DAITYO_KB_MAX_LENGTH);
	}
	public String getDaityoKbString()
	{
		return "'" + cutString(this.daityo_kb,DAITYO_KB_MAX_LENGTH) + "'";
	}
	public String getDaityoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.daityo_kb,DAITYO_KB_MAX_LENGTH));
	}
	
	// entry_kino_kbに対するセッターとゲッターの集合
	public boolean setIraiNo(String irai_no)
	{
		this.irai_no = irai_no;
		return true;
	}
	public String getIraiNo()
	{
		return cutString(this.irai_no,IRAI_NO_MAX_LENGTH);
	}
	public String getIraiNoString()
	{
		return "'" + cutString(this.irai_no,IRAI_NO_MAX_LENGTH) + "'";
	}
	public String getIraiNoHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.irai_no,IRAI_NO_MAX_LENGTH));
	}
	
	// ctlColorに対するセッターとゲッターの集合
	public boolean setCtlColor(Map ctlColor)
	{
		this.ctlColor = ctlColor;
		return true;
	}
	public Map getCtlColor()
	{
		return this.ctlColor;
	}
	
	// disbaleに対するセッターとゲッターの集合
	public boolean setDisbale(String disbale)
	{
		this.disbale = disbale;
		return true;
	}
	public String getDisbale()
	{
		return this.disbale;
	}
	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return this.sentaku;
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
			+ "  tenpo_nm = " + getTenpoNmString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  unit_nm = " + getUnitNmString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  ps_syohin_na = " + getPsSyohinNaString()
			+ "  daisi_no_nb = " + getDaisiNoNbString()
			+ "  ps_maisu_qt = " + getPsMaisuQtString()
			+ " pc_kb = " + getPcKbString()
			+ " bumon_cd = " + getBumonCdString()
			+ " syokai_user_id = " + getSyokaiUserIdString()
			+ " update_ts = " + getUpdateTs()
			+ " sentaku = " + getSentaku()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mstA90101_PsTanpinHakkouRequestBean コピー後のクラス
	 */
	public mstA90101_PsTanpinHakkouRequestBean createClone()
	{
		mstA90101_PsTanpinHakkouRequestBean bean = new mstA90101_PsTanpinHakkouRequestBean();
		bean.setSendDt(this.send_dt);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNm(this.tenpo_nm);
		bean.setUnitCd(this.unit_cd);
		bean.setUnitNm(this.unit_nm);
		bean.setSyohinCd(this.syohin_cd);
		bean.setPsSyohinNa(this.ps_syohin_na);
		bean.setDaisiNoNb(this.daisi_no_nb);
		bean.setPsMaisuQt(this.ps_maisu_qt);
		bean.setPcKb(this.pc_kb);
		bean.setBumonCd(this.bumon_cd);
		bean.setSyokaiUserId(this.syokai_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setSentaku(this.sentaku);
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
		if( !( o instanceof mstA90101_PsTanpinHakkouRequestBean ) ) return false;
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
}
