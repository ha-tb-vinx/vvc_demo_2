/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820101用テーマ別アイテム一覧の画面項目格納用クラス</P>
 * <p>説明: 新ＭＤシステムで使用するテーマ別アイテム一覧のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/07)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム　マスターメンテナンス　mst820101用テーマ別アイテム一覧のレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用するテーマ別アイテム一覧のレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/07)初版作成
 */
public class mst820101_ThemeItemBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int KEIRYO_HANKU_CD_MAX_LENGTH = 1;//計量販区コードの長さ
	public static final int SYOHIN_YOBIDASI_MAX_LENGTH = 4;//呼出NOの長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;//小業種コード (FK)の長さ
	public static final int THEME_CD_MAX_LENGTH = 2;//テーマコード (FK)の長さ
	public static final int HANEI_DT_MAX_LENGTH = 8;//反映日の長さ
	public static final int SYOHIN_KBN_MAX_LENGTH = 1;//商品区分の長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int KEIRYOKI_NA_MAX_LENGTH = 50;//計量器名称の長さ
	public static final int RECEIPT_HINMEI_NA_MAX_LENGTH = 30;//レシート品名の長さ
	public static final int TEIGAKU_UP_KB_MAX_LENGTH = 1;//定額・UP区分の長さ
	public static final int TEIGAKUJI_TANI_KB_MAX_LENGTH = 2;//定額時単位区分の長さ
	public static final int SANTI_KB_MAX_LENGTH = 3;//産地区分の長さ
	public static final int SYOMIKIKAN_KB_MAX_LENGTH = 1;//賞味期間計算区分の長さ
	public static final int KAKOJIKOKU_PRINT_KB_MAX_LENGTH = 1;//加工時刻印字区分の長さ
	public static final int CHORIYO_KOKOKUBUN_KB_MAX_LENGTH = 3;//調理用広告文の長さ
	public static final int HOZON_ONDOTAI_KB_MAX_LENGTH = 2;//保存温度帯区分の長さ
	public static final int START_KB_MAX_LENGTH = 2;//開始日付区分の長さ
	public static final int BACK_LABEL_KB_MAX_LENGTH = 1;//裏面ラベル項目文区分の長さ
	public static final int EIYO_SEIBUN_NA_MAX_LENGTH = 30;//栄養成分表示の長さ
	public static final int COMMENT_KB_MAX_LENGTH = 2;//コメント区分の長さ
	public static final int BIKO_TX_MAX_LENGTH = 100;//備考の長さ
	public static final int GENZAIRYO_NA_MAX_LENGTH = 864;//原材料名称の長さ
	public static final int TENKABUTU_NA_MAX_LENGTH = 864;//添加物名称の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String keiryo_hanku_cd = null;	//計量販区コード
	private String syohin_yobidasi = null;	//呼出NO
	private String s_gyosyu_cd = null;	//小業種コード (FK)
	private String theme_cd = null;	//テーマコード (FK)
	private String hanei_dt = null;	//反映日
	private String syohin_kbn = null;	//商品区分
	private String syohin_cd = null;	//商品コード
	private String keiryoki_na = null;	//計量器名称
	private String receipt_hinmei_na = null;	//レシート品名
	private String teigaku_up_kb = null;	//定額・UP区分
	private long tanka_vl = 0;	//単価
	private long teigaku_vl = 0;	//定額時内容量
	private String teigakuji_tani_kb = null;	//定額時単位区分
	private String syomikikan_kb = null;	//賞味期間計算区分
	private long syomikikan_vl = 0;	//賞味期間
	private String santi_kb = null;	//産地番号
	private String kakojikoku_print_kb = null;	//加工時刻印字区分
	private String choriyo_kokokubun_kb = null;	//調理用広告文
	private String hozon_ondotai_kb = null;	//保存温度帯区分
	private String start_kb = null;	//開始日付区分
	private String back_label_kb = null;	//裏面ラベル項目文区分
	private String eiyo_seibun_na = null;	//栄養成分表示
	private String comment_kb = null;	//コメント区分
	private String biko_tx = null;	//備考
	private String genzairyo_na = null;	//原材料名称
	private String tenkabutu_na = null;	//添加物名称
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ

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
	 * RKeiryokiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst820101_ThemeItemBean getRKeiryokiBean(DataHolder dataHolder)
	{
		mst820101_ThemeItemBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst820101_ThemeItemDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst820101_ThemeItemBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// keiryo_hanku_cdに対するセッターとゲッターの集合
	public boolean setKeiryoHankuCd(String keiryo_hanku_cd)
	{
		this.keiryo_hanku_cd = keiryo_hanku_cd;
		return true;
	}
	public String getKeiryoHankuCd()
	{
		return cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH);
	}
	public String getKeiryoHankuCdString()
	{
		return "'" + cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getKeiryoHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryo_hanku_cd,KEIRYO_HANKU_CD_MAX_LENGTH));
	}


	// syohin_yobidasiに対するセッターとゲッターの集合
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
	public String getSyohinYobidasi()
	{
		return cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH);
	}
	public String getSyohinYobidasiString()
	{
		return "'" + cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH) + "'";
	}
	public String getSyohinYobidasiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH));
	}


	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// theme_cdに対するセッターとゲッターの集合
	public boolean setThemeCd(String theme_cd)
	{
		this.theme_cd = theme_cd;
		return true;
	}
	public String getThemeCd()
	{
		return cutString(this.theme_cd,THEME_CD_MAX_LENGTH);
	}
	public String getThemeCdString()
	{
		return "'" + cutString(this.theme_cd,THEME_CD_MAX_LENGTH) + "'";
	}
	public String getThemeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.theme_cd,THEME_CD_MAX_LENGTH));
	}


	// hanei_dtに対するセッターとゲッターの集合
	public boolean setHaneiDt(String hanei_dt)
	{
		this.hanei_dt = hanei_dt;
		return true;
	}
	public String getHaneiDt()
	{
		return cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH);
	}
	public String getHaneiDtString()
	{
		return "'" + cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH) + "'";
	}
	public String getHaneiDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanei_dt,HANEI_DT_MAX_LENGTH));
	}


	// syohin_kbnに対するセッターとゲッターの集合
	public boolean setSyohinKbn(String syohin_kbn)
	{
		this.syohin_kbn = syohin_kbn;
		return true;
	}
	public String getSyohinKbn()
	{
		return cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH);
	}
	public String getSyohinKbnString()
	{
		return "'" + cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH) + "'";
	}
	public String getSyohinKbnHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_kbn,SYOHIN_KBN_MAX_LENGTH));
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


	// keiryoki_naに対するセッターとゲッターの集合
	public boolean setKeiryokiNa(String keiryoki_na)
	{
		this.keiryoki_na = keiryoki_na;
		return true;
	}
	public String getKeiryokiNa()
	{
		return cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH);
	}
	public String getKeiryokiNaString()
	{
		return "'" + cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH) + "'";
	}
	public String getKeiryokiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH));
	}


	// receipt_hinmei_naに対するセッターとゲッターの集合
	public boolean setReceiptHinmeiNa(String receipt_hinmei_na)
	{
		this.receipt_hinmei_na = receipt_hinmei_na;
		return true;
	}
	public String getReceiptHinmeiNa()
	{
		return cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH);
	}
	public String getReceiptHinmeiNaString()
	{
		return "'" + cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH) + "'";
	}
	public String getReceiptHinmeiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.receipt_hinmei_na,RECEIPT_HINMEI_NA_MAX_LENGTH));
	}


	// teigaku_up_kbに対するセッターとゲッターの集合
	public boolean setTeigakuUpKb(String teigaku_up_kb)
	{
		this.teigaku_up_kb = teigaku_up_kb;
		return true;
	}
	public String getTeigakuUpKb()
	{
		return cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH);
	}
	public String getTeigakuUpKbString()
	{
		return "'" + cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakuUpKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH));
	}


	// tanka_vlに対するセッターとゲッターの集合
	public boolean setTankaVl(String tanka_vl)
	{
		try
		{
			this.tanka_vl = Long.parseLong(tanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTankaVl(long tanka_vl)
	{
		this.tanka_vl = tanka_vl;
		return true;
	}
	public long getTankaVl()
	{
		return this.tanka_vl;
	}
	public String getTankaVlString()
	{
		return Long.toString(this.tanka_vl);
	}


	// teigaku_vlに対するセッターとゲッターの集合
	public boolean setTeigakuVl(String teigaku_vl)
	{
		try
		{
			this.teigaku_vl = Long.parseLong(teigaku_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeigakuVl(long teigaku_vl)
	{
		this.teigaku_vl = teigaku_vl;
		return true;
	}
	public long getTeigakuVl()
	{
		return this.teigaku_vl;
	}
	public String getTeigakuVlString()
	{
		return Long.toString(this.teigaku_vl);
	}


	// teigakuji_tani_kbに対するセッターとゲッターの集合
	public boolean setTeigakujiTaniKb(String teigakuji_tani_kb)
	{
		this.teigakuji_tani_kb = teigakuji_tani_kb;
		return true;
	}
	public String getTeigakujiTaniKb()
	{
		return cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH);
	}
	public String getTeigakujiTaniKbString()
	{
		return "'" + cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakujiTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH));
	}


	// syomikikan_kbに対するセッターとゲッターの集合
	public boolean setSyomikikanKb(String syomikikan_kb)
	{
		this.syomikikan_kb = syomikikan_kb;
		return true;
	}
	public String getSyomikikanKb()
	{
		return cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH);
	}
	public String getSyomikikanKbString()
	{
		return "'" + cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getSyomikikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syomikikan_kb,SYOMIKIKAN_KB_MAX_LENGTH));
	}


	// syomikikan_vlに対するセッターとゲッターの集合
	public boolean setSyomikikanVl(String syomikikan_vl)
	{
		try
		{
			this.syomikikan_vl = Long.parseLong(syomikikan_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyomikikanVl(long syomikikan_vl)
	{
		this.syomikikan_vl = syomikikan_vl;
		return true;
	}
	public long getSyomikikanVl()
	{
		return this.syomikikan_vl;
	}
	public String getSyomikikanVlString()
	{
		return Long.toString(this.syomikikan_vl);
	}


	// santi_kbに対するセッターとゲッターの集合
	public boolean setSantiKb(String santi_kb)
	{
		this.santi_kb = santi_kb;
		return true;
	}
	public String getSantiKb()
	{
		return cutString(this.santi_kb,SANTI_KB_MAX_LENGTH);
	}
	public String getSantiKbString()
	{
		return "'" + cutString(this.santi_kb,SANTI_KB_MAX_LENGTH) + "'";
	}
	public String getSantiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.santi_kb,SANTI_KB_MAX_LENGTH));
	}


	// kakojikoku_print_kbに対するセッターとゲッターの集合
	public boolean setKakojikokuPrintKb(String kakojikoku_print_kb)
	{
		this.kakojikoku_print_kb = kakojikoku_print_kb;
		return true;
	}
	public String getKakojikokuPrintKb()
	{
		return cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH);
	}
	public String getKakojikokuPrintKbString()
	{
		return "'" + cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH) + "'";
	}
	public String getKakojikokuPrintKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kakojikoku_print_kb,KAKOJIKOKU_PRINT_KB_MAX_LENGTH));
	}


	// choriyo_kokokubun_kbに対するセッターとゲッターの集合
	public boolean setChoriyoKokokubunKb(String choriyo_kokokubun_kb)
	{
		this.choriyo_kokokubun_kb = choriyo_kokokubun_kb;
		return true;
	}
	public String getChoriyoKokokubunKb()
	{
		return cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH);
	}
	public String getChoriyoKokokubunKbString()
	{
		return "'" + cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH) + "'";
	}
	public String getChoriyoKokokubunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH));
	}


	// hozon_ondotai_kbに対するセッターとゲッターの集合
	public boolean setHozonOndotaiKb(String hozon_ondotai_kb)
	{
		this.hozon_ondotai_kb = hozon_ondotai_kb;
		return true;
	}
	public String getHozonOndotaiKb()
	{
		return cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH);
	}
	public String getHozonOndotaiKbString()
	{
		return "'" + cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getHozonOndotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH));
	}


	// start_kbに対するセッターとゲッターの集合
	public boolean setStartKb(String start_kb)
	{
		this.start_kb = start_kb;
		return true;
	}
	public String getStartKb()
	{
		return cutString(this.start_kb,START_KB_MAX_LENGTH);
	}
	public String getStartKbString()
	{
		return "'" + cutString(this.start_kb,START_KB_MAX_LENGTH) + "'";
	}
	public String getStartKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.start_kb,START_KB_MAX_LENGTH));
	}


	// back_label_kbに対するセッターとゲッターの集合
	public boolean setBackLabelKb(String back_label_kb)
	{
		this.back_label_kb = back_label_kb;
		return true;
	}
	public String getBackLabelKb()
	{
		return cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH);
	}
	public String getBackLabelKbString()
	{
		return "'" + cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH) + "'";
	}
	public String getBackLabelKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.back_label_kb,BACK_LABEL_KB_MAX_LENGTH));
	}


	// eiyo_seibun_naに対するセッターとゲッターの集合
	public boolean setEiyoSeibunNa(String eiyo_seibun_na)
	{
		this.eiyo_seibun_na = eiyo_seibun_na;
		return true;
	}
	public String getEiyoSeibunNa()
	{
		return cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH);
	}
	public String getEiyoSeibunNaString()
	{
		return "'" + cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH) + "'";
	}
	public String getEiyoSeibunNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eiyo_seibun_na,EIYO_SEIBUN_NA_MAX_LENGTH));
	}


	// comment_kbに対するセッターとゲッターの集合
	public boolean setCommentKb(String comment_kb)
	{
		this.comment_kb = comment_kb;
		return true;
	}
	public String getCommentKb()
	{
		return cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH);
	}
	public String getCommentKbString()
	{
		return "'" + cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH) + "'";
	}
	public String getCommentKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH));
	}


	// biko_txに対するセッターとゲッターの集合
	public boolean setBikoTx(String biko_tx)
	{
		this.biko_tx = biko_tx;
		return true;
	}
	public String getBikoTx()
	{
		return cutString(this.biko_tx,BIKO_TX_MAX_LENGTH);
	}
	public String getBikoTxString()
	{
		return "'" + cutString(this.biko_tx,BIKO_TX_MAX_LENGTH) + "'";
	}
	public String getBikoTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.biko_tx,BIKO_TX_MAX_LENGTH));
	}


	// genzairyo_naに対するセッターとゲッターの集合
	public boolean setGenzairyoNa(String genzairyo_na)
	{
		this.genzairyo_na = genzairyo_na;
		return true;
	}
	public String getGenzairyoNa()
	{
		return cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH);
	}
	public String getGenzairyoNaString()
	{
		return "'" + cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH) + "'";
	}
	public String getGenzairyoNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.genzairyo_na,GENZAIRYO_NA_MAX_LENGTH));
	}


	// tenkabutu_naに対するセッターとゲッターの集合
	public boolean setTenkabutuNa(String tenkabutu_na)
	{
		this.tenkabutu_na = tenkabutu_na;
		return true;
	}
	public String getTenkabutuNa()
	{
		return cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH);
	}
	public String getTenkabutuNaString()
	{
		return "'" + cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH) + "'";
	}
	public String getTenkabutuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenkabutu_na,TENKABUTU_NA_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  keiryo_hanku_cd = " + getKeiryoHankuCdString()
			+ "  syohin_yobidasi = " + getSyohinYobidasiString()
			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  theme_cd = " + getThemeCdString()
			+ "  hanei_dt = " + getHaneiDtString()
			+ "  syohin_kbn = " + getSyohinKbnString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  keiryoki_na = " + getKeiryokiNaString()
			+ "  receipt_hinmei_na = " + getReceiptHinmeiNaString()
			+ "  teigaku_up_kb = " + getTeigakuUpKbString()
			+ "  tanka_vl = " + getTankaVlString()
			+ "  teigaku_vl = " + getTeigakuVlString()
			+ "  teigakuji_tani_kb = " + getTeigakujiTaniKbString()
			+ "  syomikikan_kb = " + getSyomikikanKbString()
			+ "  syomikikan_vl = " + getSyomikikanVlString()
			+ "  santi_kb = " + getSantiKbString()
			+ "  kakojikoku_print_kb = " + getKakojikokuPrintKbString()
			+ "  choriyo_kokokubun_kb = " + getChoriyoKokokubunKbString()
			+ "  hozon_ondotai_kb = " + getHozonOndotaiKbString()
			+ "  start_kb = " + getStartKbString()
			+ "  back_label_kb = " + getBackLabelKbString()
			+ "  eiyo_seibun_na = " + getEiyoSeibunNaString()
			+ "  comment_kb = " + getCommentKbString()
			+ "  biko_tx = " + getBikoTxString()
			+ "  genzairyo_na = " + getGenzairyoNaString()
			+ "  tenkabutu_na = " + getTenkabutuNaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RKeiryokiBean コピー後のクラス
	 */
	public mst820101_ThemeItemBean createClone()
	{
		mst820101_ThemeItemBean bean = new mst820101_ThemeItemBean();
		bean.setKeiryoHankuCd(this.keiryo_hanku_cd);
		bean.setSyohinYobidasi(this.syohin_yobidasi);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setThemeCd(this.theme_cd);
		bean.setHaneiDt(this.hanei_dt);
		bean.setSyohinKbn(this.syohin_kbn);
		bean.setSyohinCd(this.syohin_cd);
		bean.setKeiryokiNa(this.keiryoki_na);
		bean.setReceiptHinmeiNa(this.receipt_hinmei_na);
		bean.setTeigakuUpKb(this.teigaku_up_kb);
		bean.setTankaVl(this.tanka_vl);
		bean.setTeigakuVl(this.teigaku_vl);
		bean.setTeigakujiTaniKb(this.teigakuji_tani_kb);
		bean.setSyomikikanKb(this.syomikikan_kb);
		bean.setSyomikikanVl(this.syomikikan_vl);
		bean.setSantiKb(this.santi_kb);
		bean.setKakojikokuPrintKb(this.kakojikoku_print_kb);
		bean.setChoriyoKokokubunKb(this.choriyo_kokokubun_kb);
		bean.setHozonOndotaiKb(this.hozon_ondotai_kb);
		bean.setStartKb(this.start_kb);
		bean.setBackLabelKb(this.back_label_kb);
		bean.setEiyoSeibunNa(this.eiyo_seibun_na);
		bean.setCommentKb(this.comment_kb);
		bean.setBikoTx(this.biko_tx);
		bean.setGenzairyoNa(this.genzairyo_na);
		bean.setTenkabutuNa(this.tenkabutu_na);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
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
		if( !( o instanceof mst820101_ThemeItemBean ) ) return false;
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
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
