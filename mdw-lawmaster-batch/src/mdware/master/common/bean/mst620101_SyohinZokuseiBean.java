/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
public class mst620101_SyohinZokuseiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int GYOSYU_KB_MAX_LENGTH = 3;//業種区分の長さ
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 18;//漢字品名の長さ

	public static final int D_ZOKUSEI_1_NA_MAX_LENGTH = 3;//大属性１の長さ
	public static final int S_ZOKUSEI_1_NA_MAX_LENGTH = 3;//小属性１の長さ
	public static final int D_ZOKUSEI_2_NA_MAX_LENGTH = 3;//大属性２の長さ
	public static final int S_ZOKUSEI_2_NA_MAX_LENGTH = 3;//小属性２の長さ
	public static final int D_ZOKUSEI_3_NA_MAX_LENGTH = 3;//大属性３の長さ
	public static final int S_ZOKUSEI_3_NA_MAX_LENGTH = 3;//小属性３の長さ
	public static final int D_ZOKUSEI_4_NA_MAX_LENGTH = 3;//大属性４の長さ
	public static final int S_ZOKUSEI_4_NA_MAX_LENGTH = 3;//小属性４の長さ
	public static final int D_ZOKUSEI_5_NA_MAX_LENGTH = 3;//大属性５の長さ
	public static final int S_ZOKUSEI_5_NA_MAX_LENGTH = 3;//小属性５の長さ
	public static final int D_ZOKUSEI_6_NA_MAX_LENGTH = 3;//大属性６の長さ
	public static final int S_ZOKUSEI_6_NA_MAX_LENGTH = 3;//小属性６の長さ
	public static final int D_ZOKUSEI_7_NA_MAX_LENGTH = 3;//大属性７の長さ
	public static final int S_ZOKUSEI_7_NA_MAX_LENGTH = 3;//小属性７の長さ
	public static final int D_ZOKUSEI_8_NA_MAX_LENGTH = 3;//大属性８の長さ
	public static final int S_ZOKUSEI_8_NA_MAX_LENGTH = 3;//小属性８の長さ
	public static final int D_ZOKUSEI_9_NA_MAX_LENGTH = 3;//大属性９の長さ
	public static final int S_ZOKUSEI_9_NA_MAX_LENGTH = 3;//小属性９の長さ
	public static final int D_ZOKUSEI_10_NA_MAX_LENGTH = 3;//大属性１０の長さ
	public static final int S_ZOKUSEI_10_NA_MAX_LENGTH = 3;//小属性１０の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ

	private String hanku_cd = null;	//販区コード
	private String syohin_cd = null;	//商品コード
	private String yuko_dt = null;	//有効日
	private String gyosyu_kb = null;	//業種区分
	private String hinmei_kanji_na = null;	//漢字品名
	private String d_zokusei_1_na = null;	//大属性１
	private String s_zokusei_1_na = null;	//小属性１
	private String d_zokusei_2_na = null;	//大属性２
	private String s_zokusei_2_na = null;	//小属性２
	private String d_zokusei_3_na = null;	//大属性３
	private String s_zokusei_3_na = null;	//小属性３
	private String d_zokusei_4_na = null;	//大属性４
	private String s_zokusei_4_na = null;	//小属性４
	private String d_zokusei_5_na = null;	//大属性５
	private String s_zokusei_5_na = null;	//小属性５
	private String d_zokusei_6_na = null;	//大属性６
	private String s_zokusei_6_na = null;	//小属性６
	private String d_zokusei_7_na = null;	//大属性７
	private String s_zokusei_7_na = null;	//小属性７
	private String d_zokusei_8_na = null;	//大属性８
	private String s_zokusei_8_na = null;	//小属性８
	private String d_zokusei_9_na = null;	//大属性９
	private String s_zokusei_9_na = null;	//小属性９
	private String d_zokusei_10_na = null;	//大属性１０
	private String s_zokusei_10_na = null;	//小属性１０
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
//BUGNO-S017 2005.04.22 S.Takahashi START
	private String sentaku1				= null;	//選択１
	private String sentaku2				= null;	//選択２
	private String sentaku3				= null;	//選択３
	private String sentaku4				= null;	//選択４
	private String sentaku5				= null;	//選択５
	private String sentaku6				= null;	//選択６
	private String sentaku7				= null;	//選択７
	private String sentaku8				= null;	//選択８
	private String sentaku9				= null;	//選択９
	private String sentaku10				= null;	//選択１０
//BUGNO-S017 2005.04.22 S.Takahashi END
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
	 * RSyohinBeanを１件のみ抽出したい時に使用する
	 */
	public static mst620101_SyohinZokuseiBean getRSyohinBean(DataHolder dataHolder)
	{
		mst620101_SyohinZokuseiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst620101_SyohinZokuseiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst620101_SyohinZokuseiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// hanku_cdに対するセッターとゲッターの集合
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	public String getHankuCd()
	{
		return cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH);
	}
	public String getHankuCdString()
	{
		return "'" + cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_cd,HANKU_CD_MAX_LENGTH));
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


	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt,YUKO_DT_MAX_LENGTH));
	}


	// gyosyu_kbに対するセッターとゲッターの集合
	public boolean setGyosyuKb(String gyosyu_kb)
	{
		this.gyosyu_kb = gyosyu_kb;
		return true;
	}
	public String getGyosyuKb()
	{
		return cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH);
	}
	public String getGyosyuKbString()
	{
		return "'" + cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH) + "'";
	}
	public String getGyosyuKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyosyu_kb,GYOSYU_KB_MAX_LENGTH));
	}


	// hinmei_kanji_naに対するセッターとゲッターの集合
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
	public String getHinmeiKanjiNa()
	{
		return cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH);
	}
	public String getHinmeiKanjiNaString()
	{
		return "'" + cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getHinmeiKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinmei_kanji_na,HINMEI_KANJI_NA_MAX_LENGTH));
	}


	// d_zokusei_1_naに対するセッターとゲッターの集合
	public boolean setDZokusei1Na(String d_zokusei_1_na)
	{
		this.d_zokusei_1_na = d_zokusei_1_na;
		return true;
	}
	public String getDZokusei1Na()
	{
		return cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH);
	}
	public String getDZokusei1NaString()
	{
		return "'" + cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_1_na,D_ZOKUSEI_1_NA_MAX_LENGTH));
	}


	// s_zokusei_1_naに対するセッターとゲッターの集合
	public boolean setSZokusei1Na(String s_zokusei_1_na)
	{
		this.s_zokusei_1_na = s_zokusei_1_na;
		return true;
	}
	public String getSZokusei1Na()
	{
		return cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH);
	}
	public String getSZokusei1NaString()
	{
		return "'" + cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei1NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_1_na,S_ZOKUSEI_1_NA_MAX_LENGTH));
	}


	// d_zokusei_2_naに対するセッターとゲッターの集合
	public boolean setDZokusei2Na(String d_zokusei_2_na)
	{
		this.d_zokusei_2_na = d_zokusei_2_na;
		return true;
	}
	public String getDZokusei2Na()
	{
		return cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH);
	}
	public String getDZokusei2NaString()
	{
		return "'" + cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_2_na,D_ZOKUSEI_2_NA_MAX_LENGTH));
	}


	// s_zokusei_2_naに対するセッターとゲッターの集合
	public boolean setSZokusei2Na(String s_zokusei_2_na)
	{
		this.s_zokusei_2_na = s_zokusei_2_na;
		return true;
	}
	public String getSZokusei2Na()
	{
		return cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH);
	}
	public String getSZokusei2NaString()
	{
		return "'" + cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei2NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_2_na,S_ZOKUSEI_2_NA_MAX_LENGTH));
	}


	// d_zokusei_3_naに対するセッターとゲッターの集合
	public boolean setDZokusei3Na(String d_zokusei_3_na)
	{
		this.d_zokusei_3_na = d_zokusei_3_na;
		return true;
	}
	public String getDZokusei3Na()
	{
		return cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH);
	}
	public String getDZokusei3NaString()
	{
		return "'" + cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_3_na,D_ZOKUSEI_3_NA_MAX_LENGTH));
	}


	// s_zokusei_3_naに対するセッターとゲッターの集合
	public boolean setSZokusei3Na(String s_zokusei_3_na)
	{
		this.s_zokusei_3_na = s_zokusei_3_na;
		return true;
	}
	public String getSZokusei3Na()
	{
		return cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH);
	}
	public String getSZokusei3NaString()
	{
		return "'" + cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei3NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_3_na,S_ZOKUSEI_3_NA_MAX_LENGTH));
	}


	// d_zokusei_4_naに対するセッターとゲッターの集合
	public boolean setDZokusei4Na(String d_zokusei_4_na)
	{
		this.d_zokusei_4_na = d_zokusei_4_na;
		return true;
	}
	public String getDZokusei4Na()
	{
		return cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH);
	}
	public String getDZokusei4NaString()
	{
		return "'" + cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei4NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_4_na,D_ZOKUSEI_4_NA_MAX_LENGTH));
	}


	// s_zokusei_4_naに対するセッターとゲッターの集合
	public boolean setSZokusei4Na(String s_zokusei_4_na)
	{
		this.s_zokusei_4_na = s_zokusei_4_na;
		return true;
	}
	public String getSZokusei4Na()
	{
		return cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH);
	}
	public String getSZokusei4NaString()
	{
		return "'" + cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei4NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_4_na,S_ZOKUSEI_4_NA_MAX_LENGTH));
	}


	// d_zokusei_5_naに対するセッターとゲッターの集合
	public boolean setDZokusei5Na(String d_zokusei_5_na)
	{
		this.d_zokusei_5_na = d_zokusei_5_na;
		return true;
	}
	public String getDZokusei5Na()
	{
		return cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH);
	}
	public String getDZokusei5NaString()
	{
		return "'" + cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei5NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_5_na,D_ZOKUSEI_5_NA_MAX_LENGTH));
	}


	// s_zokusei_5_naに対するセッターとゲッターの集合
	public boolean setSZokusei5Na(String s_zokusei_5_na)
	{
		this.s_zokusei_5_na = s_zokusei_5_na;
		return true;
	}
	public String getSZokusei5Na()
	{
		return cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH);
	}
	public String getSZokusei5NaString()
	{
		return "'" + cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei5NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_5_na,S_ZOKUSEI_5_NA_MAX_LENGTH));
	}


	// d_zokusei_6_naに対するセッターとゲッターの集合
	public boolean setDZokusei6Na(String d_zokusei_6_na)
	{
		this.d_zokusei_6_na = d_zokusei_6_na;
		return true;
	}
	public String getDZokusei6Na()
	{
		return cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH);
	}
	public String getDZokusei6NaString()
	{
		return "'" + cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei6NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_6_na,D_ZOKUSEI_6_NA_MAX_LENGTH));
	}


	// s_zokusei_6_naに対するセッターとゲッターの集合
	public boolean setSZokusei6Na(String s_zokusei_6_na)
	{
		this.s_zokusei_6_na = s_zokusei_6_na;
		return true;
	}
	public String getSZokusei6Na()
	{
		return cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH);
	}
	public String getSZokusei6NaString()
	{
		return "'" + cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei6NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_6_na,S_ZOKUSEI_6_NA_MAX_LENGTH));
	}


	// d_zokusei_7_naに対するセッターとゲッターの集合
	public boolean setDZokusei7Na(String d_zokusei_7_na)
	{
		this.d_zokusei_7_na = d_zokusei_7_na;
		return true;
	}
	public String getDZokusei7Na()
	{
		return cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH);
	}
	public String getDZokusei7NaString()
	{
		return "'" + cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei7NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_7_na,D_ZOKUSEI_7_NA_MAX_LENGTH));
	}


	// s_zokusei_7_naに対するセッターとゲッターの集合
	public boolean setSZokusei7Na(String s_zokusei_7_na)
	{
		this.s_zokusei_7_na = s_zokusei_7_na;
		return true;
	}
	public String getSZokusei7Na()
	{
		return cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH);
	}
	public String getSZokusei7NaString()
	{
		return "'" + cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei7NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_7_na,S_ZOKUSEI_7_NA_MAX_LENGTH));
	}


	// d_zokusei_8_naに対するセッターとゲッターの集合
	public boolean setDZokusei8Na(String d_zokusei_8_na)
	{
		this.d_zokusei_8_na = d_zokusei_8_na;
		return true;
	}
	public String getDZokusei8Na()
	{
		return cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH);
	}
	public String getDZokusei8NaString()
	{
		return "'" + cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei8NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_8_na,D_ZOKUSEI_8_NA_MAX_LENGTH));
	}


	// s_zokusei_8_naに対するセッターとゲッターの集合
	public boolean setSZokusei8Na(String s_zokusei_8_na)
	{
		this.s_zokusei_8_na = s_zokusei_8_na;
		return true;
	}
	public String getSZokusei8Na()
	{
		return cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH);
	}
	public String getSZokusei8NaString()
	{
		return "'" + cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei8NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_8_na,S_ZOKUSEI_8_NA_MAX_LENGTH));
	}


	// d_zokusei_9_naに対するセッターとゲッターの集合
	public boolean setDZokusei9Na(String d_zokusei_9_na)
	{
		this.d_zokusei_9_na = d_zokusei_9_na;
		return true;
	}
	public String getDZokusei9Na()
	{
		return cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH);
	}
	public String getDZokusei9NaString()
	{
		return "'" + cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei9NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_9_na,D_ZOKUSEI_9_NA_MAX_LENGTH));
	}


	// s_zokusei_9_naに対するセッターとゲッターの集合
	public boolean setSZokusei9Na(String s_zokusei_9_na)
	{
		this.s_zokusei_9_na = s_zokusei_9_na;
		return true;
	}
	public String getSZokusei9Na()
	{
		return cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH);
	}
	public String getSZokusei9NaString()
	{
		return "'" + cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei9NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_9_na,S_ZOKUSEI_9_NA_MAX_LENGTH));
	}


	// d_zokusei_10_naに対するセッターとゲッターの集合
	public boolean setDZokusei10Na(String d_zokusei_10_na)
	{
		this.d_zokusei_10_na = d_zokusei_10_na;
		return true;
	}
	public String getDZokusei10Na()
	{
		return cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH);
	}
	public String getDZokusei10NaString()
	{
		return "'" + cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH) + "'";
	}
	public String getDZokusei10NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.d_zokusei_10_na,D_ZOKUSEI_10_NA_MAX_LENGTH));
	}


	// s_zokusei_10_naに対するセッターとゲッターの集合
	public boolean setSZokusei10Na(String s_zokusei_10_na)
	{
		this.s_zokusei_10_na = s_zokusei_10_na;
		return true;
	}
	public String getSZokusei10Na()
	{
		return cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH);
	}
	public String getSZokusei10NaString()
	{
		return "'" + cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH) + "'";
	}
	public String getSZokusei10NaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_zokusei_10_na,S_ZOKUSEI_10_NA_MAX_LENGTH));
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

//BUGNO-S017 2005.04.22 S.Takahashi START
  /**
   * 選択1に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku1("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku1(String string) {
	  sentaku1 = string;
  }
  /**
   * 選択1に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku1();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku1() {
	  return sentaku1;
  }


  /**
   * 選択1に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku2("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku2(String string) {
	  sentaku2 = string;
  }
  /**
   * 選択1に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku2();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku2() {
	  return sentaku2;
  }


  /**
   * 選択1に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku3("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku3(String string) {
	  sentaku3 = string;
  }
  /**
   * 選択1に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku3();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku3() {
	  return sentaku3;
  }


  /**
   * 選択1に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku4("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku4(String string) {
	  sentaku4 = string;
  }
  /**
   * 選択1に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku4();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku4() {
	  return sentaku4;
  }


  /**
   * 選択4に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku5("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku5(String string) {
	  sentaku5 = string;
  }
  /**
   * 選択5に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku5();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku5() {
	  return sentaku5;
  }


  /**
   * 選択6に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku6("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku6(String string) {
	  sentaku6 = string;
  }
  /**
   * 選択6に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku6();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku6() {
	  return sentaku6;
  }


  /**
   * 選択7に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku7("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku7(String string) {
	  sentaku7 = string;
  }
  /**
   * 選択7に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku7();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku7() {
	  return sentaku7;
  }


  /**
   * 選択8に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku8("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku8(String string) {
	  sentaku8 = string;
  }
  /**
   * 選択8に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku8();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku8() {
	  return sentaku8;
  }


  /**
   * 選択9に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku9("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku9(String string) {
	  sentaku9 = string;
  }
  /**
   * 選択9に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku9();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku9() {
	  return sentaku9;
  }


  /**
   * 選択10に対するセッター<br>
   * <br>
   * Ex)<br>
   * getSentaku10("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSentaku10(String string) {
	  sentaku10 = string;
  }
  /**
   * 選択10に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSentaku10();　戻り値	文字列<br>
   * <br>
   * @param String 設定する文字列
   */
  public String getSentaku10() {
	  return sentaku10;
  }
//BUGNO-S017 2005.04.22 S.Takahashi END
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  hanku_cd = " + getHankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  gyosyu_kb = " + getGyosyuKbString()
			+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
			+ "  d_zokusei_1_na = " + getDZokusei1NaString()
			+ "  s_zokusei_1_na = " + getSZokusei1NaString()
			+ "  d_zokusei_2_na = " + getDZokusei2NaString()
			+ "  s_zokusei_2_na = " + getSZokusei2NaString()
			+ "  d_zokusei_3_na = " + getDZokusei3NaString()
			+ "  s_zokusei_3_na = " + getSZokusei3NaString()
			+ "  d_zokusei_4_na = " + getDZokusei4NaString()
			+ "  s_zokusei_4_na = " + getSZokusei4NaString()
			+ "  d_zokusei_5_na = " + getDZokusei5NaString()
			+ "  s_zokusei_5_na = " + getSZokusei5NaString()
			+ "  d_zokusei_6_na = " + getDZokusei6NaString()
			+ "  s_zokusei_6_na = " + getSZokusei6NaString()
			+ "  d_zokusei_7_na = " + getDZokusei7NaString()
			+ "  s_zokusei_7_na = " + getSZokusei7NaString()
			+ "  d_zokusei_8_na = " + getDZokusei8NaString()
			+ "  s_zokusei_8_na = " + getSZokusei8NaString()
			+ "  d_zokusei_9_na = " + getDZokusei9NaString()
			+ "  s_zokusei_9_na = " + getSZokusei9NaString()
			+ "  d_zokusei_10_na = " + getDZokusei10NaString()
			+ "  s_zokusei_10_na = " + getSZokusei10NaString()
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
	 * @return RSyohinBean コピー後のクラス
	 */
	public mst620101_SyohinZokuseiBean createClone()
	{
		mst620101_SyohinZokuseiBean bean = new mst620101_SyohinZokuseiBean();
		bean.setHankuCd(this.hanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setGyosyuKb(this.gyosyu_kb);
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);
		bean.setDZokusei1Na(this.d_zokusei_1_na);
		bean.setSZokusei1Na(this.s_zokusei_1_na);
		bean.setDZokusei2Na(this.d_zokusei_2_na);
		bean.setSZokusei2Na(this.s_zokusei_2_na);
		bean.setDZokusei3Na(this.d_zokusei_3_na);
		bean.setSZokusei3Na(this.s_zokusei_3_na);
		bean.setDZokusei4Na(this.d_zokusei_4_na);
		bean.setSZokusei4Na(this.s_zokusei_4_na);
		bean.setDZokusei5Na(this.d_zokusei_5_na);
		bean.setSZokusei5Na(this.s_zokusei_5_na);
		bean.setDZokusei6Na(this.d_zokusei_6_na);
		bean.setSZokusei6Na(this.s_zokusei_6_na);
		bean.setDZokusei7Na(this.d_zokusei_7_na);
		bean.setSZokusei7Na(this.s_zokusei_7_na);
		bean.setDZokusei8Na(this.d_zokusei_8_na);
		bean.setSZokusei8Na(this.s_zokusei_8_na);
		bean.setDZokusei9Na(this.d_zokusei_9_na);
		bean.setSZokusei9Na(this.s_zokusei_9_na);
		bean.setDZokusei10Na(this.d_zokusei_10_na);
		bean.setSZokusei10Na(this.s_zokusei_10_na);
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
		if( !( o instanceof mst620101_SyohinZokuseiBean ) ) return false;
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
