package mdware.common.bean;

import java.util.*;
import java.math.BigDecimal;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.07.12) Version 1.0.IST_CUSTOM.2
 * @version X.X (Create time: 2004/8/5 14:40:24)
 */
public class MaSyohinBean
{
	private static final String NULL_VAL = "";

	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SYOHIN_CD_MAX_LENGTH             = 20;
	public static final int BEGIN_DT_MAX_LENGTH              =  8;
	public static final int NOHIN_SYOHIN_CD_MAX_LENGTH       = 20;
	public static final int POS_CD_MAX_LENGTH                = 20;
	public static final int JISYA_SYOHIN_CD_MAX_LENGTH       = 20;
	public static final int SYOHIN_NA_MAX_LENGTH             = 80;
	public static final int SYOHIN_KA_MAX_LENGTH             = 50;
	public static final int KIKAKU_NA_MAX_LENGTH             = 20;
	public static final int KIKAKU_KA_MAX_LENGTH             =  6;
	public static final int L_HANKU_CD_MAX_LENGTH            =  4;
	public static final int HINSYU_CD_MAX_LENGTH             =  4;
	public static final int HINSYU_NA_MAX_LENGTH             = 36;
	public static final int HINMOKU_CD_MAX_LENGTH            =  4;
	public static final int HINMOKU_NA_MAX_LENGTH            = 40;
	public static final int HINMOKU_KA_MAX_LENGTH            = 20;
	public static final int COLOR_KA_MAX_LENGTH              =  5;
	public static final int SIZE_KA_MAX_LENGTH               =  7;
	public static final int TORIHIKISAKI_CD_MAX_LENGTH       = 11;
	public static final int TORIHIKISAKI_NA_MAX_LENGTH       = 60;
	public static final int MAKER_CD_MAX_LENGTH              =  4;
	public static final int KETASU_KB_MAX_LENGTH             =  1;
	public static final int TEIKAN_KB_MAX_LENGTH             =  1;
	public static final int SYOMIKIKAN_KB_MAX_LENGTH         =  1;
	public static final int SYOMIKIKAN_MAX_LENGTH            =  3;
	public static final int ONDOTAI_KB_MAX_LENGTH            =  1;
	public static final int HANBAI_BEGIN_DT_MAX_LENGTH       =  8;
	public static final int HANBAI_END_DT_MAX_LENGTH         =  8;
	public static final int NEFUDA_KB_MAX_LENGTH             =  1;
	public static final int PC_KB_MAX_LENGTH                 =  1;
	public static final int PC_TANI_KB_MAX_LENGTH            =  2;
	public static final int EOS_KB_MAX_LENGTH                =  1;
	public static final int HAISO_KB_MAX_LENGTH              =  1;
	public static final int KB_SOBA_KB_MAX_LENGTH            =  1;
	public static final int HANBAI_KIKAN_KB_MAX_LENGTH       =  1;
	public static final int HACHU_TEISI_KB_MAX_LENGTH        =  1;
	public static final int SIYOFUKA_KB_MAX_LENGTH           =  1;
	public static final int INSERT_DT_MAX_LENGTH             =  8;
	public static final int UPDATE_DT_MAX_LENGTH             =  8;
	public static final int RIYO_USER_ID_MAX_LENGTH          = 20;
	public static final int INSERT_TS_MAX_LENGTH             = 20;
	public static final int UPDATE_TS_MAX_LENGTH             = 20;

	public static final int HACHU_TANI_QT_SCALE              = 0;
	public static final int IRISU_QT_SCALE                   = 1;
	public static final int GENTANKA_VL_SCALE                = 2;
	public static final int SYUKO_GENTANKA_VL_SCALE          = 2;

	public static final int HACHU_TANI_QT_MODE               = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int IRISU_QT_MODE                    = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int GENTANKA_VL_MODE                 = BigDecimal.ROUND_DOWN; // 切り捨て
	public static final int SYUKO_GENTANKA_VL_MODE           = BigDecimal.ROUND_DOWN; // 切り捨て

	private String syohin_cd = null;
	private String begin_dt = null;
	private String nohin_syohin_cd = null;
	private String pos_cd = null;
	private String jisya_syohin_cd = null;
	private String syohin_na = null;
	private String syohin_ka = null;
	private String kikaku_na = null;
	private String kikaku_ka = null;
	private String l_hanku_cd = null;
	private String hinsyu_cd = null;
	private String hinsyu_na = null;
	private String hinmoku_cd = null;
	private String hinmoku_na = null;
	private String hinmoku_ka = null;
	private String color_ka = null;
	private String size_ka = null;
	private String torihikisaki_cd = null;
	private String torihikisaki_na = null;
	private String maker_cd = null;
	private String ketasu_kb = null;
	private BigDecimal hachu_tani_qt = null;
	private BigDecimal irisu_qt = null;
	private String teikan_kb = null;
	private String syomikikan_kb = null;
	private String syomikikan = null;
	private String ondotai_kb = null;
	private BigDecimal gentanka_vl = null;
	private BigDecimal syuko_gentanka_vl = null;
	private long baitanka_vl = 0;
	private String hanbai_begin_dt = null;
	private String hanbai_end_dt = null;
	private String nefuda_kb = null;
	private String pc_kb = null;
	private String pc_tani_kb = null;
	private long pc_tani_suryo = 0;
	private long pc_kijun_tani = 0;
	private String eos_kb = null;
	private String haiso_kb = null;
	private String kb_soba_kb = null;
	private long bin_rt_1 = 0;
	private long bin_rt_2 = 0;
	private long bin_rt_3 = 0;
	private long bin_rt_4 = 0;
	private String hanbai_kikan_kb = null;
	private String hachu_teisi_kb = null;
	private String siyofuka_kb = null;
	private String insert_dt = null;
	private String update_dt = null;
	private String riyo_user_id = null;
	private String insert_ts = null;
	private String update_ts = null;
	private String hibrid_tenhanku_cd = null;

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
	 * MaSyohinBeanを１件のみ抽出したい時に使用する
	 */
	public static MaSyohinBean getMaSyohinBean(DataHolder dataHolder)
	{
		MaSyohinBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new MaSyohinDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MaSyohinBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// begin_dtに対するセッターとゲッターの集合
	public boolean setBeginDt(String begin_dt)
	{
		this.begin_dt = begin_dt;
		return true;
	}
	public String getBeginDt()
	{
		return cutString(this.begin_dt,BEGIN_DT_MAX_LENGTH);
	}
	public String getBeginDtString()
	{
		return "'" + cutString(this.begin_dt,BEGIN_DT_MAX_LENGTH) + "'";
	}
	public String getBeginDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.begin_dt,BEGIN_DT_MAX_LENGTH));
	}


	// nohin_syohin_cdに対するセッターとゲッターの集合
	public boolean setNohinSyohinCd(String nohin_syohin_cd)
	{
		this.nohin_syohin_cd = nohin_syohin_cd;
		return true;
	}
	public String getNohinSyohinCd()
	{
		return cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH);
	}
	public String getNohinSyohinCdString()
	{
		return "'" + cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getNohinSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_syohin_cd,NOHIN_SYOHIN_CD_MAX_LENGTH));
	}


	// pos_cdに対するセッターとゲッターの集合
	public boolean setPosCd(String pos_cd)
	{
		this.pos_cd = pos_cd;
		return true;
	}
	public String getPosCd()
	{
		return cutString(this.pos_cd,POS_CD_MAX_LENGTH);
	}
	public String getPosCdString()
	{
		return "'" + cutString(this.pos_cd,POS_CD_MAX_LENGTH) + "'";
	}
	public String getPosCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pos_cd,POS_CD_MAX_LENGTH));
	}


	// jisya_syohin_cdに対するセッターとゲッターの集合
	public boolean setJisyaSyohinCd(String jisya_syohin_cd)
	{
		this.jisya_syohin_cd = jisya_syohin_cd;
		return true;
	}
	public String getJisyaSyohinCd()
	{
		return cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH);
	}
	public String getJisyaSyohinCdString()
	{
		return "'" + cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getJisyaSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.jisya_syohin_cd,JISYA_SYOHIN_CD_MAX_LENGTH));
	}


	// syohin_naに対するセッターとゲッターの集合
	public boolean setSyohinNa(String syohin_na)
	{
		this.syohin_na = syohin_na;
		return true;
	}
	public String getSyohinNa()
	{
		return cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH);
	}
	public String getSyohinNaString()
	{
		return "'" + cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH) + "'";
	}
	public String getSyohinNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_na,SYOHIN_NA_MAX_LENGTH));
	}


	// syohin_kaに対するセッターとゲッターの集合
	public boolean setSyohinKa(String syohin_ka)
	{
		this.syohin_ka = syohin_ka;
		return true;
	}
	public String getSyohinKa()
	{
		return cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH);
	}
	public String getSyohinKaString()
	{
		return "'" + cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH) + "'";
	}
	public String getSyohinKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_ka,SYOHIN_KA_MAX_LENGTH));
	}


	// kikaku_naに対するセッターとゲッターの集合
	public boolean setKikakuNa(String kikaku_na)
	{
		this.kikaku_na = kikaku_na;
		return true;
	}
	public String getKikakuNa()
	{
		return cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH);
	}
	public String getKikakuNaString()
	{
		return "'" + cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH) + "'";
	}
	public String getKikakuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_na,KIKAKU_NA_MAX_LENGTH));
	}


	// kikaku_kaに対するセッターとゲッターの集合
	public boolean setKikakuKa(String kikaku_ka)
	{
		this.kikaku_ka = kikaku_ka;
		return true;
	}
	public String getKikakuKa()
	{
		return cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH);
	}
	public String getKikakuKaString()
	{
		return "'" + cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH) + "'";
	}
	public String getKikakuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kikaku_ka,KIKAKU_KA_MAX_LENGTH));
	}


	// l_hanku_cdに対するセッターとゲッターの集合
	public boolean setLHankuCd(String l_hanku_cd)
	{
		this.l_hanku_cd = l_hanku_cd;
		return true;
	}
	public String getLHankuCd()
	{
		return cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH);
	}
	public String getLHankuCdString()
	{
		return "'" + cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getLHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.l_hanku_cd,L_HANKU_CD_MAX_LENGTH));
	}


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
	}


	// hinsyu_naに対するセッターとゲッターの集合
	public boolean setHinsyuNa(String hinsyu_na)
	{
		this.hinsyu_na = hinsyu_na;
		return true;
	}
	public String getHinsyuNa()
	{
		return cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH);
	}
	public String getHinsyuNaString()
	{
		return "'" + cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH) + "'";
	}
	public String getHinsyuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_na,HINSYU_NA_MAX_LENGTH));
	}


	// hinmoku_cdに対するセッターとゲッターの集合
	public boolean setHinmokuCd(String hinmoku_cd)
	{
		this.hinmoku_cd = hinmoku_cd;
		return true;
	}
	public String getHinmokuCd()
	{
		return cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH);
	}
	public String getHinmokuCdString()
	{
		return "'" + cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH) + "'";
	}
	public String getHinmokuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmoku_cd,HINMOKU_CD_MAX_LENGTH));
	}


	// hinmoku_naに対するセッターとゲッターの集合
	public boolean setHinmokuNa(String hinmoku_na)
	{
		this.hinmoku_na = hinmoku_na;
		return true;
	}
	public String getHinmokuNa()
	{
		return cutString(this.hinmoku_na,HINMOKU_NA_MAX_LENGTH);
	}
	public String getHinmokuNaString()
	{
		return "'" + cutString(this.hinmoku_na,HINMOKU_NA_MAX_LENGTH) + "'";
	}
	public String getHinmokuNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmoku_na,HINMOKU_NA_MAX_LENGTH));
	}


	// hinmoku_kaに対するセッターとゲッターの集合
	public boolean setHinmokuKa(String hinmoku_ka)
	{
		this.hinmoku_ka = hinmoku_ka;
		return true;
	}
	public String getHinmokuKa()
	{
		return cutString(this.hinmoku_ka,HINMOKU_KA_MAX_LENGTH);
	}
	public String getHinmokuKaString()
	{
		return "'" + cutString(this.hinmoku_ka,HINMOKU_KA_MAX_LENGTH) + "'";
	}
	public String getHinmokuKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmoku_ka,HINMOKU_KA_MAX_LENGTH));
	}

	// color_kaに対するセッターとゲッターの集合
	public boolean setColorKa(String color_ka)
	{
		this.color_ka = color_ka;
		return true;
	}
	public String getColorKa()
	{
		return cutString(this.color_ka,COLOR_KA_MAX_LENGTH);
	}
	public String getColorKaString()
	{
		return "'" + cutString(this.color_ka,COLOR_KA_MAX_LENGTH) + "'";
	}
	public String getColorKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_ka,COLOR_KA_MAX_LENGTH));
	}

	// size_kaに対するセッターとゲッターの集合
	public boolean setSizeKa(String size_ka)
	{
		this.size_ka = size_ka;
		return true;
	}
	public String getSizeKa()
	{
		return cutString(this.size_ka,SIZE_KA_MAX_LENGTH);
	}
	public String getSizeKaString()
	{
		return "'" + cutString(this.size_ka,SIZE_KA_MAX_LENGTH) + "'";
	}
	public String getSizeKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_ka,SIZE_KA_MAX_LENGTH));
	}

	// torihikisaki_cdに対するセッターとゲッターの集合
	public boolean setTorihikisakiCd(String torihikisaki_cd)
	{
		this.torihikisaki_cd = torihikisaki_cd;
		return true;
	}
	public String getTorihikisakiCd()
	{
		return cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH);
	}
	public String getTorihikisakiCdString()
	{
		return "'" + cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_cd,TORIHIKISAKI_CD_MAX_LENGTH));
	}


	// torihikisaki_naに対するセッターとゲッターの集合
	public boolean setTorihikisakiNa(String torihikisaki_na)
	{
		this.torihikisaki_na = torihikisaki_na;
		return true;
	}
	public String getTorihikisakiNa()
	{
		return cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH);
	}
	public String getTorihikisakiNaString()
	{
		return "'" + cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.torihikisaki_na,TORIHIKISAKI_NA_MAX_LENGTH));
	}


	// maker_cdに対するセッターとゲッターの集合
	public boolean setMakerCd(String maker_cd)
	{
		this.maker_cd = maker_cd;
		return true;
	}
	public String getMakerCd()
	{
		return cutString(this.maker_cd,MAKER_CD_MAX_LENGTH);
	}
	public String getMakerCdString()
	{
		return "'" + cutString(this.maker_cd,MAKER_CD_MAX_LENGTH) + "'";
	}
	public String getMakerCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.maker_cd,MAKER_CD_MAX_LENGTH));
	}


	// ketasu_kbに対するセッターとゲッターの集合
	public boolean setKetasuKb(String ketasu_kb)
	{
		this.ketasu_kb = ketasu_kb;
		return true;
	}
	public String getKetasuKb()
	{
		return cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH);
	}
	public String getKetasuKbString()
	{
		return "'" + cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH) + "'";
	}
	public String getKetasuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ketasu_kb,KETASU_KB_MAX_LENGTH));
	}


	// hachu_tani_qtに対するセッターとゲッターの集合
	// hachu_tani_qt に対するセッタメソッド。
	public void setHachuTaniQt(String hachu_tani_qt)
	{
		try {
			if(hachu_tani_qt == null || hachu_tani_qt.length() == 0) {
				this.hachu_tani_qt = null;
			} else {
				setHachuTaniQt(new BigDecimal(hachu_tani_qt));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setHachuTaniQt(BigDecimal hachu_tani_qt) {
		this.hachu_tani_qt = hachu_tani_qt;
	}
	public void setHachuTaniQt(long hachu_tani_qt) {
		setHachuTaniQt(String.valueOf(hachu_tani_qt));
	}
	public void setHachuTaniQt(double hachu_tani_qt) {
		setHachuTaniQt(String.valueOf(hachu_tani_qt));
	}
	// hachu_tani_qt に対するゲッタメソッド。
	public long getHachuTaniQtLong() {
		if(hachu_tani_qt == null) {
			return 0L;
		}
		return hachu_tani_qt.longValue();
	}
	public double getHachuTaniQtDouble() {
		if(hachu_tani_qt == null) {
			return 0.0;
		}
		return hachu_tani_qt.doubleValue();
	}
	public String getHachuTaniQtString() {
		if(hachu_tani_qt == null) {
			return NULL_VAL;
		}
		return (hachu_tani_qt.setScale(HACHU_TANI_QT_SCALE, HACHU_TANI_QT_MODE)).toString();
	}
	public BigDecimal getHachuTaniQt() {
		return hachu_tani_qt;
	}
	public BigDecimal getHachuTaniQt(int roundingMode) {
		if(hachu_tani_qt == null) {
			return null;
		}
		return (hachu_tani_qt.setScale(HACHU_TANI_QT_SCALE, roundingMode));
	}


	// irisu_qtに対するセッターとゲッターの集合
	// irisu_qt に対するセッタメソッド。
	public void setIrisuQt(String irisu_qt)
	{
		try {
			if(irisu_qt == null || irisu_qt.length() == 0) {
				this.irisu_qt = null;
			} else {
				setIrisuQt(new BigDecimal(irisu_qt));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setIrisuQt(BigDecimal irisu_qt) {
		this.irisu_qt = irisu_qt;
	}
	public void setIrisuQt(long irisu_qt) {
		setIrisuQt(String.valueOf(irisu_qt));
	}
	public void setIrisuQt(double irisu_qt) {
		setIrisuQt(String.valueOf(irisu_qt));
	}
	// irisu_qt に対するゲッタメソッド。
	public long getIrisuQtLong() {
		if(irisu_qt == null) {
			return 0L;
		}
		return irisu_qt.longValue();
	}
	public double getIrisuQtDouble() {
		if(irisu_qt == null) {
			return 0.0;
		}
		return irisu_qt.doubleValue();
	}
	public String getIrisuQtString() {
		if(irisu_qt == null) {
			return NULL_VAL;
		}
		return (irisu_qt.setScale(IRISU_QT_SCALE, IRISU_QT_MODE)).toString();
	}
	public BigDecimal getIrisuQt() {
		return irisu_qt;
	}
	public BigDecimal getIrisuQt(int roundingMode) {
		if(irisu_qt == null) {
			return null;
		}
		return (irisu_qt.setScale(IRISU_QT_SCALE, roundingMode));
	}



	// teikan_kbに対するセッターとゲッターの集合
	public boolean setTeikanKb(String teikan_kb)
	{
		this.teikan_kb = teikan_kb;
		return true;
	}
	public String getTeikanKb()
	{
		return cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH);
	}
	public String getTeikanKbString()
	{
		return "'" + cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getTeikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teikan_kb,TEIKAN_KB_MAX_LENGTH));
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


	// syomikikanに対するセッターとゲッターの集合
	public boolean setSyomikikan(String syomikikan)
	{
		this.syomikikan = syomikikan;
		return true;
	}
	public String getSyomikikan()
	{
		return cutString(this.syomikikan,SYOMIKIKAN_MAX_LENGTH);
	}
	public String getSyomikikanString()
	{
		return "'" + cutString(this.syomikikan,SYOMIKIKAN_MAX_LENGTH) + "'";
	}
	public String getSyomikikanHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syomikikan,SYOMIKIKAN_MAX_LENGTH));
	}


	// ondotai_kbに対するセッターとゲッターの集合
	public boolean setOndotaiKb(String ondotai_kb)
	{
		this.ondotai_kb = ondotai_kb;
		return true;
	}
	public String getOndotaiKb()
	{
		return cutString(this.ondotai_kb,ONDOTAI_KB_MAX_LENGTH);
	}
	public String getOndotaiKbString()
	{
		return "'" + cutString(this.ondotai_kb,ONDOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getOndotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ondotai_kb,ONDOTAI_KB_MAX_LENGTH));
	}


	// gentanka_vlに対するセッターとゲッターの集合
	// gentanka_vl に対するセッタメソッド。
	public void setGentankaVl(String gentanka_vl)
	{
		try {
			if(gentanka_vl == null || gentanka_vl.length() == 0) {
				this.gentanka_vl = null;
			} else {
				setGentankaVl(new BigDecimal(gentanka_vl));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setGentankaVl(BigDecimal gentanka_vl) {
		this.gentanka_vl = gentanka_vl;
	}
	public void setGentankaVl(long gentanka_vl) {
		setGentankaVl(String.valueOf(gentanka_vl));
	}
	public void setGentankaVl(double gentanka_vl) {
		setGentankaVl(String.valueOf(gentanka_vl));
	}
	// gentanka_vl に対するゲッタメソッド。
	public long getGentankaVlLong() {
		if(gentanka_vl == null) {
			return 0L;
		}
		return gentanka_vl.longValue();
	}
	public double getGentankaVlDouble() {
		if(gentanka_vl == null) {
			return 0.0;
		}
		return gentanka_vl.doubleValue();
	}
	public String getGentankaVlString() {
		if(gentanka_vl == null) {
			return NULL_VAL;
		}
		return (gentanka_vl.setScale(GENTANKA_VL_SCALE, GENTANKA_VL_MODE)).toString();
	}
	public BigDecimal getGentankaVl() {
		return gentanka_vl;
	}
	public BigDecimal getGentankaVl(int roundingMode) {
		if(gentanka_vl == null) {
			return null;
		}
		return (gentanka_vl.setScale(GENTANKA_VL_SCALE, roundingMode));
	}



	// syuko_gentanka_vlに対するセッターとゲッターの集合
	public void setSyukoGentankaVl(String syuko_gentanka_vl)
	{
		try {
			if(syuko_gentanka_vl == null || syuko_gentanka_vl.length() == 0) {
				this.syuko_gentanka_vl = null;
			} else {
				setSyukoGentankaVl(new BigDecimal(syuko_gentanka_vl));
			}
		} catch(NumberFormatException e) {
			;
		}
	}
	public void setSyukoGentankaVl(BigDecimal syuko_gentanka_vl) {
		this.syuko_gentanka_vl = syuko_gentanka_vl;
	}
	public void setSyukoGentankaVl(long syuko_gentanka_vl) {
		setSyukoGentankaVl(String.valueOf(syuko_gentanka_vl));
	}
	public void setSyukoGentankaVl(double syuko_gentanka_vl) {
		setSyukoGentankaVl(String.valueOf(syuko_gentanka_vl));
	}
	// syuko_gentanka_vl に対するゲッタメソッド。
	public long getSyukoGentankaVlLong() {
		if(syuko_gentanka_vl == null) {
			return 0L;
		}
		return syuko_gentanka_vl.longValue();
	}
	public double getSyukoGentankaVlDouble() {
		if(syuko_gentanka_vl == null) {
			return 0.0;
		}
		return syuko_gentanka_vl.doubleValue();
	}
	public String getSyukoGentankaVlString() {
		if(syuko_gentanka_vl == null) {
			return NULL_VAL;
		}
		return (syuko_gentanka_vl.setScale(SYUKO_GENTANKA_VL_SCALE, SYUKO_GENTANKA_VL_MODE)).toString();
	}
	public BigDecimal getSyukoGentankaVl() {
		return syuko_gentanka_vl;
	}
	public BigDecimal getSyukoGentankaVl(int roundingMode) {
		if(syuko_gentanka_vl == null) {
			return null;
		}
		return (syuko_gentanka_vl.setScale(SYUKO_GENTANKA_VL_SCALE, roundingMode));
	}


	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		try
		{
			this.baitanka_vl = Long.parseLong(baitanka_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBaitankaVl(long baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public long getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlString()
	{
		return Long.toString(this.baitanka_vl);
	}


	// hanbai_begin_dtに対するセッターとゲッターの集合
	public boolean setHanbaiBeginDt(String hanbai_begin_dt)
	{
		this.hanbai_begin_dt = hanbai_begin_dt;
		return true;
	}
	public String getHanbaiBeginDt()
	{
		return cutString(this.hanbai_begin_dt,HANBAI_BEGIN_DT_MAX_LENGTH);
	}
	public String getHanbaiBeginDtString()
	{
		return "'" + cutString(this.hanbai_begin_dt,HANBAI_BEGIN_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiBeginDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_begin_dt,HANBAI_BEGIN_DT_MAX_LENGTH));
	}


	// hanbai_end_dtに対するセッターとゲッターの集合
	public boolean setHanbaiEndDt(String hanbai_end_dt)
	{
		this.hanbai_end_dt = hanbai_end_dt;
		return true;
	}
	public String getHanbaiEndDt()
	{
		return cutString(this.hanbai_end_dt,HANBAI_END_DT_MAX_LENGTH);
	}
	public String getHanbaiEndDtString()
	{
		return "'" + cutString(this.hanbai_end_dt,HANBAI_END_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiEndDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_end_dt,HANBAI_END_DT_MAX_LENGTH));
	}


	// nefuda_kbに対するセッターとゲッターの集合
	public boolean setNefudaKb(String nefuda_kb)
	{
		this.nefuda_kb = nefuda_kb;
		return true;
	}
	public String getNefudaKb()
	{
		return cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH);
	}
	public String getNefudaKbString()
	{
		return "'" + cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH) + "'";
	}
	public String getNefudaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefuda_kb,NEFUDA_KB_MAX_LENGTH));
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


	// pc_tani_kbに対するセッターとゲッターの集合
	public boolean setPcTaniKb(String pc_tani_kb)
	{
		this.pc_tani_kb = pc_tani_kb;
		return true;
	}
	public String getPcTaniKb()
	{
		return cutString(this.pc_tani_kb,PC_TANI_KB_MAX_LENGTH);
	}
	public String getPcTaniKbString()
	{
		return "'" + cutString(this.pc_tani_kb,PC_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getPcTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.pc_tani_kb,PC_TANI_KB_MAX_LENGTH));
	}


	// pc_tani_suryoに対するセッターとゲッターの集合
	public boolean setPcTaniSuryo(String pc_tani_suryo)
	{
		try
		{
			this.pc_tani_suryo = Long.parseLong(pc_tani_suryo);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPcTaniSuryo(long pc_tani_suryo)
	{
		this.pc_tani_suryo = pc_tani_suryo;
		return true;
	}
	public long getPcTaniSuryo()
	{
		return this.pc_tani_suryo;
	}
	public String getPcTaniSuryoString()
	{
		return Long.toString(this.pc_tani_suryo);
	}


	// pc_kijun_taniに対するセッターとゲッターの集合
	public boolean setPcKijunTani(String pc_kijun_tani)
	{
		try
		{
			this.pc_kijun_tani = Long.parseLong(pc_kijun_tani);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setPcKijunTani(long pc_kijun_tani)
	{
		this.pc_kijun_tani = pc_kijun_tani;
		return true;
	}
	public long getPcKijunTani()
	{
		return this.pc_kijun_tani;
	}
	public String getPcKijunTaniString()
	{
		return Long.toString(this.pc_kijun_tani);
	}


	// eos_kbに対するセッターとゲッターの集合
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = eos_kb;
		return true;
	}
	public String getEosKb()
	{
		return cutString(this.eos_kb,EOS_KB_MAX_LENGTH);
	}
	public String getEosKbString()
	{
		return "'" + cutString(this.eos_kb,EOS_KB_MAX_LENGTH) + "'";
	}
	public String getEosKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.eos_kb,EOS_KB_MAX_LENGTH));
	}


	// haiso_kbに対するセッターとゲッターの集合
	public boolean setHaisoKb(String haiso_kb)
	{
		this.haiso_kb = haiso_kb;
		return true;
	}
	public String getHaisoKb()
	{
		return cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH);
	}
	public String getHaisoKbString()
	{
		return "'" + cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH) + "'";
	}
	public String getHaisoKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.haiso_kb,HAISO_KB_MAX_LENGTH));
	}


	// kb_soba_kbに対するセッターとゲッターの集合
	public boolean setKbSobaKb(String kb_soba_kb)
	{
		this.kb_soba_kb = kb_soba_kb;
		return true;
	}
	public String getKbSobaKb()
	{
		return cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH);
	}
	public String getKbSobaKbString()
	{
		return "'" + cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH) + "'";
	}
	public String getKbSobaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kb_soba_kb,KB_SOBA_KB_MAX_LENGTH));
	}


	// bin_rt_1に対するセッターとゲッターの集合
	public boolean setBinRt1(String bin_rt_1)
	{
		try
		{
			this.bin_rt_1 = Long.parseLong(bin_rt_1);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBinRt1(long bin_rt_1)
	{
		this.bin_rt_1 = bin_rt_1;
		return true;
	}
	public long getBinRt1()
	{
		return this.bin_rt_1;
	}
	public String getBinRt1String()
	{
		return Long.toString(this.bin_rt_1);
	}


	// bin_rt_2に対するセッターとゲッターの集合
	public boolean setBinRt2(String bin_rt_2)
	{
		try
		{
			this.bin_rt_2 = Long.parseLong(bin_rt_2);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBinRt2(long bin_rt_2)
	{
		this.bin_rt_2 = bin_rt_2;
		return true;
	}
	public long getBinRt2()
	{
		return this.bin_rt_2;
	}
	public String getBinRt2String()
	{
		return Long.toString(this.bin_rt_2);
	}


	// bin_rt_3に対するセッターとゲッターの集合
	public boolean setBinRt3(String bin_rt_3)
	{
		try
		{
			this.bin_rt_3 = Long.parseLong(bin_rt_3);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBinRt3(long bin_rt_3)
	{
		this.bin_rt_3 = bin_rt_3;
		return true;
	}
	public long getBinRt3()
	{
		return this.bin_rt_3;
	}
	public String getBinRt3String()
	{
		return Long.toString(this.bin_rt_3);
	}


	// bin_rt_4に対するセッターとゲッターの集合
	public boolean setBinRt4(String bin_rt_4)
	{
		try
		{
			this.bin_rt_4 = Long.parseLong(bin_rt_4);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setBinRt4(long bin_rt_4)
	{
		this.bin_rt_4 = bin_rt_4;
		return true;
	}
	public long getBinRt4()
	{
		return this.bin_rt_4;
	}
	public String getBinRt4String()
	{
		return Long.toString(this.bin_rt_4);
	}


	// hanbai_kikan_kbに対するセッターとゲッターの集合
	public boolean setHanbaiKikanKb(String hanbai_kikan_kb)
	{
		this.hanbai_kikan_kb = hanbai_kikan_kb;
		return true;
	}
	public String getHanbaiKikanKb()
	{
		return cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH);
	}
	public String getHanbaiKikanKbString()
	{
		return "'" + cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getHanbaiKikanKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbai_kikan_kb,HANBAI_KIKAN_KB_MAX_LENGTH));
	}


	// hachu_teisi_kbに対するセッターとゲッターの集合
	public boolean setHachuTeisiKb(String hachu_teisi_kb)
	{
		this.hachu_teisi_kb = hachu_teisi_kb;
		return true;
	}
	public String getHachuTeisiKb()
	{
		return cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH);
	}
	public String getHachuTeisiKbString()
	{
		return "'" + cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuTeisiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH));
	}


	// siyofuka_kbに対するセッターとゲッターの集合
	public boolean setSiyofukaKb(String siyofuka_kb)
	{
		this.siyofuka_kb = siyofuka_kb;
		return true;
	}
	public String getSiyofukaKb()
	{
		return cutString(this.siyofuka_kb,SIYOFUKA_KB_MAX_LENGTH);
	}
	public String getSiyofukaKbString()
	{
		return "'" + cutString(this.siyofuka_kb,SIYOFUKA_KB_MAX_LENGTH) + "'";
	}
	public String getSiyofukaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siyofuka_kb,SIYOFUKA_KB_MAX_LENGTH));
	}


	// insert_dtに対するセッターとゲッターの集合
	public boolean setInsertDt(String insert_dt)
	{
		this.insert_dt = insert_dt;
		return true;
	}
	public String getInsertDt()
	{
		return cutString(this.insert_dt,INSERT_DT_MAX_LENGTH);
	}
	public String getInsertDtString()
	{
		return "'" + cutString(this.insert_dt,INSERT_DT_MAX_LENGTH) + "'";
	}
	public String getInsertDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_dt,INSERT_DT_MAX_LENGTH));
	}


	// update_dtに対するセッターとゲッターの集合
	public boolean setUpdateDt(String update_dt)
	{
		this.update_dt = update_dt;
		return true;
	}
	public String getUpdateDt()
	{
		return cutString(this.update_dt,UPDATE_DT_MAX_LENGTH);
	}
	public String getUpdateDtString()
	{
		return "'" + cutString(this.update_dt,UPDATE_DT_MAX_LENGTH) + "'";
	}
	public String getUpdateDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_dt,UPDATE_DT_MAX_LENGTH));
	}


	// riyo_user_idに対するセッターとゲッターの集合
	public boolean setRiyoUserId(String riyo_user_id)
	{
		this.riyo_user_id = riyo_user_id;
		return true;
	}
	public String getRiyoUserId()
	{
		return cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH);
	}
	public String getRiyoUserIdString()
	{
		return "'" + cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH) + "'";
	}
	public String getRiyoUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.riyo_user_id,RIYO_USER_ID_MAX_LENGTH));
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
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  syohin_cd = " + getSyohinCdString()
			+ "  begin_dt = " + getBeginDtString()
			+ "  nohin_syohin_cd = " + getNohinSyohinCdString()
			+ "  pos_cd = " + getPosCdString()
			+ "  jisya_syohin_cd = " + getJisyaSyohinCdString()
			+ "  syohin_na = " + getSyohinNaString()
			+ "  syohin_ka = " + getSyohinKaString()
			+ "  kikaku_na = " + getKikakuNaString()
			+ "  kikaku_ka = " + getKikakuKaString()
			+ "  l_hanku_cd = " + getLHankuCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_na = " + getHinsyuNaString()
			+ "  hinmoku_cd = " + getHinmokuCdString()
			+ "  hinmoku_na = " + getHinmokuNaString()
			+ "  hinmoku_ka = " + getHinmokuKaString()
			+ " color_ka = "         + getColorKaString()
			+ " size_ka = "          + getSizeKaString()
			+ "  torihikisaki_cd = " + getTorihikisakiCdString()
			+ "  torihikisaki_na = " + getTorihikisakiNaString()
			+ "  maker_cd = " + getMakerCdString()
			+ "  ketasu_kb = " + getKetasuKbString()
			+ "  hachu_tani_qt = " + getHachuTaniQtString()
			+ "  irisu_qt = " + getIrisuQtString()
			+ "  teikan_kb = " + getTeikanKbString()
			+ "  syomikikan_kb = " + getSyomikikanKbString()
			+ "  syomikikan = " + getSyomikikanString()
			+ "  ondotai_kb = " + getOndotaiKbString()
			+ "  gentanka_vl = " + getGentankaVlString()
			+ "  syuko_gentanka_vl = " + getSyukoGentankaVlString()
			+ "  baitanka_vl = " + getBaitankaVlString()
			+ "  hanbai_begin_dt = " + getHanbaiBeginDtString()
			+ "  hanbai_end_dt = " + getHanbaiEndDtString()
			+ "  nefuda_kb = " + getNefudaKbString()
			+ "  pc_kb = " + getPcKbString()
			+ "  pc_tani_kb = " + getPcTaniKbString()
			+ "  pc_tani_suryo = " + getPcTaniSuryoString()
			+ "  pc_kijun_tani = " + getPcKijunTaniString()
			+ "  eos_kb = " + getEosKbString()
			+ "  haiso_kb = " + getHaisoKbString()
			+ "  kb_soba_kb = " + getKbSobaKbString()
			+ "  bin_rt_1 = " + getBinRt1String()
			+ "  bin_rt_2 = " + getBinRt2String()
			+ "  bin_rt_3 = " + getBinRt3String()
			+ "  bin_rt_4 = " + getBinRt4String()
			+ "  hanbai_kikan_kb = " + getHanbaiKikanKbString()
			+ "  hachu_teisi_kb = " + getHachuTeisiKbString()
			+ "  siyofuka_kb = " + getSiyofukaKbString()
			+ "  insert_dt = " + getInsertDtString()
			+ "  update_dt = " + getUpdateDtString()
			+ "  riyo_user_id = " + getRiyoUserIdString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  hibrid_tenhanku_cd = " + getHibridTenhankuCd()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return MaSyohinBean コピー後のクラス
	 */
	public MaSyohinBean createClone()
	{
		MaSyohinBean bean = new MaSyohinBean();
		bean.setSyohinCd(this.syohin_cd);
		bean.setBeginDt(this.begin_dt);
		bean.setNohinSyohinCd(this.nohin_syohin_cd);
		bean.setPosCd(this.pos_cd);
		bean.setJisyaSyohinCd(this.jisya_syohin_cd);
		bean.setSyohinNa(this.syohin_na);
		bean.setSyohinKa(this.syohin_ka);
		bean.setKikakuNa(this.kikaku_na);
		bean.setKikakuKa(this.kikaku_ka);
		bean.setLHankuCd(this.l_hanku_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNa(this.hinsyu_na);
		bean.setHinmokuCd(this.hinmoku_cd);
		bean.setHinmokuNa(this.hinmoku_na);
		bean.setColorKa(this.color_ka);
		bean.setSizeKa(this.size_ka);
		bean.setTorihikisakiCd(this.torihikisaki_cd);
		bean.setTorihikisakiNa(this.torihikisaki_na);
		bean.setMakerCd(this.maker_cd);
		bean.setKetasuKb(this.ketasu_kb);
		bean.setHachuTaniQt(this.hachu_tani_qt);
		bean.setIrisuQt(this.irisu_qt);
		bean.setTeikanKb(this.teikan_kb);
		bean.setSyomikikanKb(this.syomikikan_kb);
		bean.setSyomikikan(this.syomikikan);
		bean.setOndotaiKb(this.ondotai_kb);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setSyukoGentankaVl(this.syuko_gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setHanbaiBeginDt(this.hanbai_begin_dt);
		bean.setHanbaiEndDt(this.hanbai_end_dt);
		bean.setNefudaKb(this.nefuda_kb);
		bean.setPcKb(this.pc_kb);
		bean.setPcTaniKb(this.pc_tani_kb);
		bean.setPcTaniSuryo(this.pc_tani_suryo);
		bean.setPcKijunTani(this.pc_kijun_tani);
		bean.setEosKb(this.eos_kb);
		bean.setHaisoKb(this.haiso_kb);
		bean.setKbSobaKb(this.kb_soba_kb);
		bean.setBinRt1(this.bin_rt_1);
		bean.setBinRt2(this.bin_rt_2);
		bean.setBinRt3(this.bin_rt_3);
		bean.setBinRt4(this.bin_rt_4);
		bean.setHanbaiKikanKb(this.hanbai_kikan_kb);
		bean.setHachuTeisiKb(this.hachu_teisi_kb);
		bean.setSiyofukaKb(this.siyofuka_kb);
		bean.setInsertDt(this.insert_dt);
		bean.setUpdateDt(this.update_dt);
		bean.setRiyoUserId(this.riyo_user_id);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
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
		if( !( o instanceof MaSyohinBean ) ) return false;
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
				byte bt[] = base.substring(pos,pos+1).getBytes("UTF-8");
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

	public String getHibridTenhankuCd() {
		return this.hibrid_tenhanku_cd;
	}

	public void setHibridTenhankuCd(String hibrid_tenhanku_cd){
		this.hibrid_tenhanku_cd = hibrid_tenhanku_cd;
	}
}
