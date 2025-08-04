/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会CSV明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する値札タグ発行情報照会CSV明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0(2006/12/25)初版作成
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）値札タグ発行情報照会CSV明細データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する値札タグ発行情報照会CSV明細データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author H.Yamamoto
 * @version 1.0(2006/12/25)初版作成
 */
public class mstB40401_NefudaTagHakkoInfoDetailBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//	↓↓2007.09.05 oohashi 本番に合わせて修正
	public static final int BUMON_CD_MAX_LENGTH = 4;
//	↑↑2007.09.05 oohashi 本番に合わせて修正
	public static final int SIIRESAKI_CD_MAX_LENGTH		 = 9;
	public static final int SIIRESAKI_NM_MAX_LENGTH		 = 80;
	public static final int HINBAN_CD_MAX_LENGTH			 = 4;
	public static final int UNIT_CD_MAX_LENGTH			 = 4;
	public static final int SIIRE_HINBAN_CD_MAX_LENGTH	 = 15;
	public static final int SYOHIN_CD_MAX_LENGTH			 = 13;
	public static final int SYOHIN_NM_MAX_LENGTH			 = 80;
	public static final int COLOR_NA_MAX_LENGTH			 = 80;	//略式名称漢字
	public static final int SIZE_NA_MAX_LENGTH 			 = 80;	//略式名称漢字
	public static final int SYOHIN_KB_NM_MAX_LENGTH		 = 20;
//	↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
	public static final int NEFUDA_KB_MAX_LENGTH			 = 1;
//	↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
	public static final int NEFUDA_KB_NM_MAX_LENGTH		 = 20;
	public static final int HANBAI_ST_DT_MAX_LENGTH		 = 8;
	public static final int HANBAI_EN_DT_MAX_LENGTH		 = 8;	
	public static final int HACHU_DT_MAX_LENGTH			 = 8;
	public static final int NOHIN_DT_MAX_LENGTH			 = 8;
	public static final int TENPO_CD_MAX_LENGTH			 = 6;

//	↓↓2007.09.05 oohashi 本番に合わせて修正
	private String bumonCd = null;
//	↑↑2007.09.05 oohashi 本番に合わせて修正
	private String siiresakiCd			 = null;
	private String siiresakiNm			 = null;
	private String hinbanCd			 = null;
	private String unitCd				 = null;
	private String siireHinbanCd		 = null;
	private String syohinCd			 = null;
	private String syohinNm			 = null;
	private String baitankaVl			 = null;
	private String keshiBaikaVl		 = null;
	private String size_na				 = null;
	private String color_na			 = null;
	private String syohinKbNm			 = null;
//	↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
	private String nefudaKb			 = null;
//	↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
	private String nefudaKbNm			 = null;
	private String hanbaiStDt			 = null;
	private String hanbaiEnDt			 = null;
	private String ruikeiKeiyakuQt		 = null;
	private String konkaiTuikeiyakuQt	 = null;
	private String hachuDt				 = null;
	private String nohinDt				 = null;
	private String tenpoCd				 = null;
	private String suryoQt				 = null;
//	↓↓2007.09.05 oohashi 本番に合わせて修正
	private List suryoQtL = new ArrayList();
//	↑↑2007.09.05 oohashi 本番に合わせて修正
	
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
	 * @param tmpStr
	 * @param num
	 * @return
	 */
	private String insertBR(String tmpStr, int num)
	{
		tmpStr = tmpStr.trim();
		if(tmpStr.length() <= num) {
			return tmpStr;		
		} else {
			if(tmpStr.length() <= num*2 ) {
				return tmpStr.substring(0,num)+"<br/>"+tmpStr.substring(num);
			} else {
				return tmpStr.substring(0,num)+"<br/>"+tmpStr.substring(num,num*2);
			}
		}
	}

//	↓↓2007.09.05 oohashi 本番に合わせて修正
    public boolean setBumonCd(String bumonCd)
    {
        this.bumonCd = bumonCd;
        return true;
    }

    public String getBumonCd()
    {
        return cutString(bumonCd, 4);
    }

    public String getBumonCdString()
    {
        return "'" + cutString(bumonCd, 4) + "'";
    }

    public String getBumonCdHTMLString()
    {
        return HTMLStringUtil.convert(cutString(bumonCd, 4));
    }
//	↑↑2007.09.05 oohashi 本番に合わせて修正
    
	// siiresakiCdに対するセッターとゲッターの集合
	public boolean setSiiresakiCd(String siiresakiCd)
	{
		this.siiresakiCd = siiresakiCd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresakiCd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresakiCd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresakiCd,SIIRESAKI_CD_MAX_LENGTH));
	}

	// siiresakiNmに対するセッターとゲッターの集合
	public boolean setSiiresakiNm(String siiresakiNm)
	{
		this.siiresakiNm = siiresakiNm;
		return true;
	}
	public String getSiiresakiNm()
	{
		return cutString(this.siiresakiNm,SIIRESAKI_NM_MAX_LENGTH);
	}
	public String getSiiresakiNmString()
	{
		return "'" + cutString(this.siiresakiNm,SIIRESAKI_NM_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresakiNm,SIIRESAKI_NM_MAX_LENGTH));
	}

	// hinbanCdに対するセッターとゲッターの集合
	public boolean setHinbanCd(String hinbanCd)
	{
		this.hinbanCd = hinbanCd;
		return true;
	}
	public String getHinbanCd()
	{
		return cutString(this.hinbanCd,HINBAN_CD_MAX_LENGTH);
	}
	public String getHinbanCdString()
	{
		return "'" + cutString(this.hinbanCd,HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinbanCd,HINBAN_CD_MAX_LENGTH));
	}

	// unitCdに対するセッターとゲッターの集合
	public boolean setUnitCd(String unitCd)
	{
		this.unitCd = unitCd;
		return true;
	}
	public String getUnitCd()
	{
		return cutString(this.unitCd,UNIT_CD_MAX_LENGTH);
	}
	public String getUnitCdString()
	{
		return "'" + cutString(this.unitCd,UNIT_CD_MAX_LENGTH) + "'";
	}
	public String getUnitCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.unitCd,UNIT_CD_MAX_LENGTH));
	}

	// siireHinbanCdに対するセッターとゲッターの集合
	public boolean setSiireHinbanCd(String siireHinbanCd)
	{
		this.siireHinbanCd = siireHinbanCd;
		return true;
	}
	public String getSiireHinbanCd()
	{
		return cutString(this.siireHinbanCd,SIIRE_HINBAN_CD_MAX_LENGTH);
	}
	public String getSiireHinbanCdString()
	{
		return "'" + cutString(this.siireHinbanCd,SIIRE_HINBAN_CD_MAX_LENGTH) + "'";
	}
	public String getSiireHinbanCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siireHinbanCd,SIIRE_HINBAN_CD_MAX_LENGTH));
	}

	// syohinCdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohinCd)
	{
		this.syohinCd = syohinCd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohinCd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohinCd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohinCd.trim(),SYOHIN_CD_MAX_LENGTH));
	}

	// syohinNmに対するセッターとゲッターの集合
	public boolean setSyohinNm(String syohinNm)
	{
		this.syohinNm = syohinNm;
		return true;
	}
	public String getSyohinNm()
	{
		return cutString(this.syohinNm,SYOHIN_NM_MAX_LENGTH);
	}
	public String getSyohinNmString()
	{
		return "'" + cutString(this.syohinNm,SYOHIN_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohinNm.trim(),SYOHIN_NM_MAX_LENGTH));
	}

	// baitankaVlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitankaVl)
	{
		this.baitankaVl = baitankaVl;
		return true;
	}
	public String getBaitankaVl()
	{
		return this.baitankaVl;
	}

	// keshiBaikaVlに対するセッターとゲッターの集合
	public boolean setKeshiBaikaVl(String keshiBaikaVl)
	{
		this.keshiBaikaVl = keshiBaikaVl;
		return true;
	}
	public String getKeshiBaikaVl()
	{
		return this.keshiBaikaVl;
	}

	// color_naに対するセッターとゲッターの集合
	public boolean setColorNa(String color_na)
	{
		this.color_na = color_na;
		return true;
	}
	public String getColorNa()
	{
		return cutString(this.color_na,COLOR_NA_MAX_LENGTH);
	}
	public String getColorNaString()
	{
		return "'" + cutString(this.color_na,COLOR_NA_MAX_LENGTH) + "'";
	}
	public String getColorNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_na,COLOR_NA_MAX_LENGTH));
	}

	// size_naに対するセッターとゲッターの集合
	public boolean setSizeNa(String size_na)
	{
		this.size_na = size_na;
		return true;
	}
	public String getSizeNa()
	{
		return cutString(this.size_na,SIZE_NA_MAX_LENGTH);
	}
	public String getSizeNaString()
	{
		return "'" + cutString(this.size_na,SIZE_NA_MAX_LENGTH) + "'";
	}
	public String getSizeNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_na,SIZE_NA_MAX_LENGTH));
	}
	
	// syohinKbNmに対するセッターとゲッターの集合
	public boolean setSyohinKbNm(String syohinKbNm)
	{
		this.syohinKbNm = syohinKbNm;
		return true;
	}
	public String getSyohinKbNm()
	{
		return cutString(this.syohinKbNm,SYOHIN_KB_NM_MAX_LENGTH);
	}
	public String getSyohinKbNmString()
	{
		return "'" + cutString(this.syohinKbNm,SYOHIN_KB_NM_MAX_LENGTH) + "'";
	}
	public String getSyohinKbNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohinKbNm,SYOHIN_KB_NM_MAX_LENGTH));
	}
	
//	↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
	// nefudaKbに対するセッターとゲッターの集合
	public boolean setNefudaKb(String nefudaKb)
	{
		this.nefudaKb = nefudaKb;
		return true;
	}
	public String getNefudaKb()
	{
		return cutString(this.nefudaKb,NEFUDA_KB_MAX_LENGTH);
	}
	public String getNefudaKbString()
	{
		return "'" + cutString(this.nefudaKb,NEFUDA_KB_MAX_LENGTH) + "'";
	}
	public String getNefudaKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefudaKb,NEFUDA_KB_MAX_LENGTH));
	}
//	↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
	
	// nefudaKbNmに対するセッターとゲッターの集合
	public boolean setNefudaKbNm(String nefudaKbNm)
	{
		this.nefudaKbNm = nefudaKbNm;
		return true;
	}
	public String getNefudaKbNm()
	{
		return cutString(this.nefudaKbNm,NEFUDA_KB_NM_MAX_LENGTH);
	}
	public String getNefudaKbNmString()
	{
		return "'" + cutString(this.nefudaKbNm,NEFUDA_KB_NM_MAX_LENGTH) + "'";
	}
	public String getNefudaKbNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nefudaKbNm,NEFUDA_KB_NM_MAX_LENGTH));
	}

	// hanbaiStDtに対するセッターとゲッターの集合
	public boolean setHanbaiStDt(String hanbaiStDt)
	{
		this.hanbaiStDt = hanbaiStDt;
		return true;
	}
	public String getHanbaiStDt()
	{
		return cutString(this.hanbaiStDt,HANBAI_ST_DT_MAX_LENGTH);
	}
	public String getHanbaiStDtString()
	{
		return "'" + cutString(this.hanbaiStDt,HANBAI_ST_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiStDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbaiStDt,HANBAI_ST_DT_MAX_LENGTH));
	}

	// hanbaiEnDtに対するセッターとゲッターの集合
	public boolean setHanbaiEnDt(String hanbaiEnDt)
	{
		this.hanbaiEnDt = hanbaiEnDt;
		return true;
	}
	public String getHanbaiEnDt()
	{
		return cutString(this.hanbaiEnDt,HANBAI_EN_DT_MAX_LENGTH);
	}
	public String getHanbaiEnDtString()
	{
		return "'" + cutString(this.hanbaiEnDt,HANBAI_EN_DT_MAX_LENGTH) + "'";
	}
	public String getHanbaiEnDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanbaiEnDt,HANBAI_EN_DT_MAX_LENGTH));
	}

	// ruikeiKeiyakuQtに対するセッターとゲッターの集合
	public boolean setRuikeiKeiyakuQt(String ruikeiKeiyakuQt)
	{
		this.ruikeiKeiyakuQt = ruikeiKeiyakuQt;
		return true;
	}
	public String getRuikeiKeiyakuQt()
	{
		return this.ruikeiKeiyakuQt;
	}

	// konkaiTuikeiyakuQtに対するセッターとゲッターの集合
	public boolean setKonkaiTuikeiyakuQt(String konkaiTuikeiyakuQt)
	{
		this.konkaiTuikeiyakuQt = konkaiTuikeiyakuQt;
		return true;
	}
	public String getKonkaiTuikeiyakuQt()
	{
		return this.konkaiTuikeiyakuQt;
	}

	// hachuDtに対するセッターとゲッターの集合
	public boolean setHachuDt(String hachuDt)
	{
		this.hachuDt = hachuDt;
		return true;
	}
	public String getHachuDt()
	{
		return cutString(this.hachuDt,HACHU_DT_MAX_LENGTH);
	}
	public String getHachuDtString()
	{
		return "'" + cutString(this.hachuDt,HACHU_DT_MAX_LENGTH) + "'";
	}
	public String getHachuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachuDt,HACHU_DT_MAX_LENGTH));
	}

	// nohinDtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohinDt)
	{
		this.nohinDt = nohinDt;
		return true;
	}
	public String getNohinDt()
	{
		return cutString(this.nohinDt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		return "'" + cutString(this.nohinDt,NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohinDt,NOHIN_DT_MAX_LENGTH));
	}

	// tenpoCdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpoCd)
	{
		this.tenpoCd = tenpoCd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpoCd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpoCd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpoCd,TENPO_CD_MAX_LENGTH));
	}

	// suryoQtに対するセッターとゲッターの集合
	public boolean setSuryoQt(String suryoQt)
	{
		this.suryoQt = suryoQt;
		return true;
	}
	public String getSuryoQt()
	{
		return this.suryoQt;
	}

//	↓↓2007.09.05 oohashi 本番に合わせて修正
    public boolean setSuryoQtL(List suryoQtL)
    {
        this.suryoQtL = suryoQtL;
        return true;
    }

    public List getSuryoQtL()
    {
        return suryoQtL;
    }
//	↑↑2007.09.05 oohashi 本番に合わせて修正
    
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  siiresakiCd = " + getSiiresakiCdString()
			+ "  siiresakiNm = " + getSiiresakiNmString()
			+ "  hinbanCd = " + getHinbanCdString()
			+ "  unitCd = " + getUnitCdString()
			+ "  siireHinbanCd = " + getSiireHinbanCdString()
			+ "  syohinCd = " + getSyohinCdString()
			+ "  syohinNm = " + getSyohinNmString()
			+ "  baitankaVl = " + getBaitankaVl()
			+ "  keshiBaikaVl = " + getKeshiBaikaVl()
			+ "  colorNa = " + getColorNaString()
			+ "  sizeNa = " + getSizeNaString()
			+ "  syohinKbNm = " + getSyohinKbNmString()
//		↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
			+ "  nefudaKb = " + getNefudaKbString()
//		↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
			+ "  nefudaKbNm = " + getNefudaKbNmString()
			+ "  hanbaiStDt = " + getHanbaiStDtString()
			+ "  hanbaiEnDt = " + getHanbaiEnDtString()
			+ "  ruikeiKeiyakuQt = " + getRuikeiKeiyakuQt()
			+ "  konkaiTuikeiyakuQt = " + getKonkaiTuikeiyakuQt()
			+ "  hachuDt = " + getHachuDtString()
			+ "  nohinDt = " + getNohinDtString()
			+ "  tenpoCd = " + getTenpoCdString()
			+ "  suryoQt = " + getSuryoQt()
			+ " createDatabase  = " + createDatabase;
	}

	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return TestBean コピー後のクラス
	 */
	public mstB40401_NefudaTagHakkoInfoDetailBean createClone()
	{
		mstB40401_NefudaTagHakkoInfoDetailBean bean = new mstB40401_NefudaTagHakkoInfoDetailBean();
		bean.setSiiresakiCd(this.siiresakiCd);
		bean.setSiiresakiNm(this.siiresakiNm);
		bean.setHinbanCd(this.hinbanCd);
		bean.setUnitCd(this.unitCd);
		bean.setSiireHinbanCd(this.siireHinbanCd);
		bean.setSyohinCd(this.syohinCd);	
		bean.setSyohinNm(this.getSyohinNm());
		bean.setBaitankaVl(this.baitankaVl);
		bean.setKeshiBaikaVl(this.keshiBaikaVl);
		bean.setColorNa(this.getColorNa());
		bean.setSizeNa(this.getSizeNa());
		bean.setSyohinKbNm(this.syohinKbNm);
//		↓↓2007.01.22 H.Yamamoto カスタマイズ修正↓↓
		bean.setNefudaKb(this.nefudaKb);
//		↑↑2007.01.22 H.Yamamoto カスタマイズ修正↑↑
		bean.setNefudaKbNm(this.nefudaKbNm);
		bean.setHanbaiStDt(this.hanbaiStDt);
		bean.setHanbaiEnDt(this.hanbaiEnDt);
		bean.setRuikeiKeiyakuQt(this.ruikeiKeiyakuQt);
		bean.setKonkaiTuikeiyakuQt(this.konkaiTuikeiyakuQt);
		bean.setHachuDt(this.hachuDt);
		bean.setNohinDt(this.nohinDt);
		bean.setTenpoCd(this.tenpoCd);
		bean.setSuryoQt(this.suryoQt);
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
		if( !( o instanceof mstB40401_NefudaTagHakkoInfoDetailBean ) ) return false;
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
