/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信画面の検索結果レコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信の検索結果レコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T. Kimura
 * @version 1.0(2005/05/10)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品一括送信画面の検索結果レコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品一括送信の検索結果レコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T. Kimura
 * @version 1.0(2005/05/10)初版作成
 */
public class mst770301_TanpinSendListBean
{
	
	
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SYORI_STS_MAX_LENGTH = 1;				//処理状況の長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;				//店舗コードの長さ
	public static final int TENPO_KANJI_RN_MAX_LENGTH = 20;			//店舗名称の長さ
//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	public static final int HINSYU_CD_MAX_LENGTH = 4;				     //単品コードの長さ
	public static final int UNIT_CD_MAX_LENGTH = 4;
	public static final int HINSYU_KANJI_RN_MAX_LENGTH = 20;		//単品名称の長さ
	public static final int SEND_DT_MAX_LENGTH = 8;					//送信日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;				//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;			//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;				//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;			//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;				//削除フラグの長さ
	public static final int SENTAKU_MAX_LENGTH = 1;					//処理選択の長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;				//商品コードの長さ
	public static final int KANJI_RN_MAX_LENGTH = 20;				//略式名称(漢字)
//	public static final int HANKU_CD_MAX_LENGTH = 4;			         //販区コードの長さ
	public static final int HINBAN_CD_MAX_LENGTH = 4;	         //品番の長さ
	public static final int BUMON_CD_MAX_LENGTH = 4;	        //部門の長さ
	public static final int PC_SEND_END_KB_MAX_LENGTH = 1;   //ＰＣ送信済区分の長さ
	public static final int REC_HINMEI_KANJI_NA = 20;				//商品名称の長さ
//	public static final int REC_HINMEI_KANNA_NA = 20;				//商品名称の長さ
	public static final int REC_HINMEI_KANA_NA=20;            //商品名称の長さ
//	public static final int HINMEI_KANNA_NA = 20;				//商品名称の長さ
	

	private String syori_sts = null;				//処理状況
	private String tenpo_cd = null;					//店舗コード
	private String tenpo_kanji_rn = null;			//店舗名称
//	private String hinsyu_cd = null;				  //単品コード
	private String unit_cd = null;	             //ユニットコード
	private String hinsyu_kanji_rn = null;			//単品名称(漢字)
	
	private long  mst_tanpin_cnt = 0;				//マスタ登録単品数
	private String send_dt = null;					//送信日
	private String insert_ts = null;				//作成年月日
	private String insert_user_id = null;			//作成者社員ID
	private String update_ts = null;				//更新年月日
	private String update_user_id = null;			//更新者社員ID
	private String delete_fg = null;				//削除フラグ
	private String sentaku = null;					//処理選択
	private String syohin_cd = null;				//商品コード
//	private String hanku_cd = null;					//販区コード
	private String bumon_cd = null;	            //部門コード
	private String hinban_cd = null;	           //品番コード
	private String pc_send_end_kb = null;	  //ＰＣ送信済区分の長さ
	private String rec_hinmei_kanji_na = null;		//商品名称(漢字)
	private String rec_hinmei_kana_na = null;		//商品名称(カナ)
	private String kanji_rn = null;	          //略式名称(漢字)
	//商品名称をレシート商品名、なければ、カナ品名に変更 2006-03-23 M.Kawamoto
//	private String rec_hinmei_kanna_na = null;			//商品名称(レシートカナ)
//	private String hinmei_kanna_na = null;			//商品名称(レシートカナ)
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
	

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
	 * mst770301_TanpinSendListBeanを１件のみ抽出したい時に使用する
	 */
	public static mst770301_TanpinSendListBean getmst770301_TanpinSendListBean(DataHolder dataHolder)
	{
		mst770301_TanpinSendListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst770301_TanpinSendListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst770301_TanpinSendListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	// hanku_cdに対するセッターとゲッターの集合
//	public boolean setHankuCd(String hanku_cd)
//	{
//		this.hanku_cd = hanku_cd;
//		return true;
//	}
//	public String getHankuCd()
//	{
//		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
//	}
//	public String getHankuCdString()
//	{
//		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHankuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
//	}
//hinaban_cdに対するセッターとゲッターの集合
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

	
//bumon_cdに対するセッターとゲッターの集合
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
	

//	pc_send_end_kbに対するセッターとゲッターの集合
	public boolean setPc_send_end_KB(String pc_send_end_kb)
	{
		this.pc_send_end_kb = pc_send_end_kb;
		return true;
	}
	public String getPc_send_end_KB()
	{
		return cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH);
	}
	public String getPc_send_end_KBString()
	{
		return "'" + cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH) + "'";
	}
	public String getPc_send_end_KBHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_send_end_kb,PC_SEND_END_KB_MAX_LENGTH));
	}
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑	
	
	
	// syori_stsに対するセッターとゲッターの集合
	public boolean setSyoriSts(String syori_sts)
	{
		this.syori_sts = syori_sts;
		return true;
	}
	public String getSyoriSts()
	{
		return cutString(this.syori_sts,SYORI_STS_MAX_LENGTH);
	}
	public String getSyoriStsString()
	{
		return "'" + cutString(this.syori_sts,SYORI_STS_MAX_LENGTH) + "'";
	}
	public String getSyoriStsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syori_sts,SYORI_STS_MAX_LENGTH));
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


	// tenpo_kanji_rnに対するセッターとゲッターの集合
	public boolean setTenpoKanjiRn(String tenpo_kanji_rn)
	{
		this.tenpo_kanji_rn = tenpo_kanji_rn;
		return true;
	}
	public String getTenpoKanjiRn()
	{
		return cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH);
	}
	public String getTenpoKanjiRnString()
	{
		return "'" + cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getTenpoKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_kanji_rn,TENPO_KANJI_RN_MAX_LENGTH));
	}


//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	// hinsyu_cdに対するセッターとゲッターの集合
//	public boolean setHinsyuCd(String hinsyu_cd)
//	{
//		this.hinsyu_cd = hinsyu_cd;
//		return true;
//	}
//	public String getHinsyuCd()
//	{
//		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
//	}
//	public String getHinsyuCdString()
//	{
//		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
//	}
//	public String getHinsyuCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
//	}
//	 unit_cdに対するセッターとゲッターの集合	
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
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unit_cd,UNIT_CD_MAX_LENGTH));
	}
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
	// rec_hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setRecHinmeiKanjiNa(String rec_hinmei_kanji_na)
	{
		this.rec_hinmei_kanji_na = rec_hinmei_kanji_na;
		return true;
	}
	public String getRecHinmeiKanjiNa()
	{
		return cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA);
	}
	public String getRecHinmeiKanjiNaString()
	{
		return "'" + cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA) + "'";
	}
	public String getRecHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kanji_na,REC_HINMEI_KANJI_NA));
	}
//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//	// rec_hinmei_kanna_naに対するセッターとゲッターの集合
//	public boolean setRecHinmeiKannaNa(String rec_hinmei_kanna_na)
//	{
//		this.rec_hinmei_kanna_na = rec_hinmei_kanna_na;
//		return true;
//	}
//	public String getRecHinmeiKannaNa()
//	{
//		return cutString(this.rec_hinmei_kanna_na,REC_HINMEI_KANNA_NA);
//	}
//	public String getRecHinmeiKannaNaString()
//	{
//		return "'" + cutString(this.rec_hinmei_kanna_na,REC_HINMEI_KANNA_NA) + "'";
//	}
//	public String getRecHinmeiKannaNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kanna_na,REC_HINMEI_KANNA_NA));
//	}
//
//	// hinmei_kanji_naに対するセッターとゲッターの集合
//	public boolean setHinmeiKannaNa(String hinmei_kanna_na)
//	{
//		this.hinmei_kanna_na = hinmei_kanna_na;
//		return true;
//	}
//	public String getHinmeiKannaNa()
//	{
//		return cutString(this.hinmei_kanna_na,HINMEI_KANNA_NA);
//	}
//	public String getHinmeiKannaNaString()
//	{
//		return "'" + cutString(this.hinmei_kanna_na,HINMEI_KANNA_NA) + "'";
//	}
//	public String getHinmeiKannaNaHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.hinmei_kanna_na,HINMEI_KANNA_NA));
//	}		
//  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
	// rec_hinmei_kanna_naに対するセッターとゲッターの集合
	public boolean setRecHinmeiKanaNa(String rec_hinmei_kana_na)
	{
		this.rec_hinmei_kana_na = rec_hinmei_kana_na;
		return true;
	}
	public String getRecHinmeiKannaNa()
	{
		return cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA);
	}
	public String getRecHinmeiKannaNaString()
	{
		return "'" + cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA) + "'";
	}
	public String getRecHinmeiKannaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.rec_hinmei_kana_na,REC_HINMEI_KANA_NA));
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
	
	// kanji_rnに対するセッターとゲッターの集合
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
	public String getKanjiRn()
	{
		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
	}
	public String getKanjiRnString()
	{
		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
	}
	
	public boolean setHinsyuKanjiRn(String hinsyu_kanji_rn)
	{
		this.hinsyu_kanji_rn = hinsyu_kanji_rn;
		return true;
	}
	public String getHinsyuKanjiRn()
	{
		return cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH);
	}
	public String getHinsyuKanjiRnString()
	{
		return "'" + cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH) + "'";
	}
	public String getHinsyuKanjiRnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_kanji_rn,HINSYU_KANJI_RN_MAX_LENGTH));
	}
	// mst_tanpin_cntに対するセッターとゲッターの集合
	public boolean setMstTanpinCnt(String mst_tanpin_cnt)
	{
		try
		{
			this.mst_tanpin_cnt = Long.parseLong(mst_tanpin_cnt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setMstTanpinCnt(long mst_tanpin_cnt)
	{
		this.mst_tanpin_cnt = mst_tanpin_cnt;
		return true;
	}
	public long getMstTanpinCnt()
	{
		return this.mst_tanpin_cnt;
	}
	public String getMstTanpinCntString()
	{
		return Long.toString(this.mst_tanpin_cnt);
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

	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}

	
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return
			  "  syori_sts = " + getSyoriStsString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_kanji_rn = " + getTenpoKanjiRnString()
//  ↓↓2006.06.22 lulh カスタマイズ修正↓↓
//		    + "  hinsyu_cd = " + getHinsyuCdString()
			+ "  unit_cd = " + getUnitCdString()
			+ "  mst_tanpin_cnt = " + getMstTanpinCntString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ "  sentaku = " + getSentaku()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst770301_TanpinSendListBean コピー後のクラス
	 */
	public mst770301_TanpinSendListBean createClone()
	{
		mst770301_TanpinSendListBean bean = new mst770301_TanpinSendListBean();
		bean.setSyoriSts(this.getSyoriSts());
		bean.setTenpoCd(this.getTenpoCd());
		bean.setTenpoKanjiRn(this.getTenpoKanjiRn());
//		bean.setHinsyuCd(this.getHinsyuCd());
		bean.setUnitCd(this.getUnitCd());
//	  ↑↑2006.06.22 lulh カスタマイズ修正↑↑
		bean.setHinsyuKanjiRn(this.getHinsyuKanjiRn());
		bean.setMstTanpinCnt(this.getMstTanpinCnt());
		bean.setInsertTs(this.getInsertTs());
		bean.setInsertUserId(this.getInsertUserId());
		bean.setUpdateTs(this.getUpdateTs());
		bean.setUpdateUserId(this.getUpdateUserId());
		bean.setDeleteFg(this.getDeleteFg());
		bean.setSentaku(this.getSentaku());
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
		if( !( o instanceof mst770301_TanpinSendListBean ) ) return false;
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
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
