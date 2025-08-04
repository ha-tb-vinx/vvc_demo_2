/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Jozawa START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）商品マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/19)初版作成
 */
public class mst620201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private String hanku_cd 				= null;	//販区コード
	private String sv_hanku_cd 			= null;	//販区コードSAVE
	private String hanku_nm 				= null;	//販区コード名
	private String syohin_cd 				= null;	//商品コード
	private String sv_syohin_cd 			= null;	//商品コードSAVE
	private String yuko_dt 				= null;	//有効日
	private String gyosyu_kb 				= null;	//業種区分
	private String hinmei_kanji_na			= null;	//漢字品名
	private String d_zokusei_1_na 			= null;	//大属性１
	private String s_zokusei_1_na 			= null;	//小属性１
	private String sentaku1				= null;	//選択１
	private String d_zokusei_2_na 			= null;	//大属性２
	private String s_zokusei_2_na 			= null;	//小属性２
	private String sentaku2				= null;	//選択２
	private String d_zokusei_3_na 			= null;	//大属性３
	private String s_zokusei_3_na 			= null;	//小属性３
	private String sentaku3				= null;	//選択３
	private String d_zokusei_4_na 			= null;	//大属性４
	private String s_zokusei_4_na 			= null;	//小属性４
	private String sentaku4				= null;	//選択４
	private String d_zokusei_5_na 			= null;	//大属性５
	private String s_zokusei_5_na 			= null;	//小属性５
	private String sentaku5				= null;	//選択５
	private String d_zokusei_6_na 			= null;	//大属性６
	private String s_zokusei_6_na 			= null;	//小属性６
	private String sentaku6				= null;	//選択６
	private String d_zokusei_7_na 			= null;	//大属性７
	private String s_zokusei_7_na 			= null;	//小属性７
	private String sentaku7				= null;	//選択７
	private String d_zokusei_8_na 			= null;	//大属性８
	private String s_zokusei_8_na 			= null;	//小属性８
	private String sentaku8				= null;	//選択８
	private String d_zokusei_9_na 			= null;	//大属性９
	private String s_zokusei_9_na 			= null;	//小属性９
	private String sentaku9				= null;	//選択９
	private String d_zokusei_10_na 		= null;	//大属性１０
	private String s_zokusei_10_na			= null;	//小属性１０
	private String sentaku10				= null;	//選択１０
	private String insert_ts 				= null;	//作成年月日
	private String insert_user_id 			= null;	//作成者社員ID
	private String update_ts 				= null;	//更新年月日
	private String update_user_id 			= null;	//更新者社員ID
	private String delete_fg 				= null;	//削除フラグ
	
	private String processingdivision 		= null;	//処理状況
	private String errorflg 				= null;	//エラーフラグ
	private String errormessage 			= null;	//エラーメッセージ
	private String[] menubar 				= null;	//メニューバーアイテム
	private Map mode 						= new HashMap();	//処理モード
	private String firstfocus 				= null;	//フォーカスを最初に取得するオブジェクト名
	private String insertflg 				= null;	//新規処理利用可能区分
	private String updateflg 				= null;	//更新処理利用可能区分
	private String deleteflg 				= null;	//削除処理利用可能区分
	private String referenceflg 			= null;	//照会処理利用可能区分
	private String csvflg 					= null;	//CSV処理利用可能区分
	private String printflg 				= null;	//印刷処理利用可能区分
	private String before_disp_id 			= null;	//前画面ID
	private String disp_url 				= null;	//現画面URL
	private String checkflg 				= null;	//チェック処理判断
	private String existflg 				= null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searcherrorflg 			= null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateprocessflg 		= null;	//更新処理内容
	private Map ctrlcolor					= new HashMap();
	private String syohinyukodt			= null;	//商品マスタより取得した有効日
	private String changeflg				= null;	//変更フラグ

//BUGNO-S052 2005.05.14 Y.Jozawa START
	private static final String INIT_PAGE = "mst620101_SyohinZokuseiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst620201_SyohinZokuseiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst620301_SyohinZokuseiView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";		// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Jozawa END

/**
 * 販区コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setHankuCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
/**
 * 販区コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHankuCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHankuCd()
	{
		return this.hanku_cd;
	}
	/**
	 * 販区コードSAVEに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSvHankuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setSvHankuCd(String sv_hanku_cd)
		{
			this.sv_hanku_cd = sv_hanku_cd;
			return true;
		}
	/**
	 * 販区コードSAVEに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSvHankuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSvHankuCd()
		{
			return this.sv_hanku_cd;
		}


	/**
	 * 販区コード名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHankuNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setHankuNm(String hanku_nm)
		{
			this.hanku_nm = hanku_nm;
			return true;
		}
	/**
	 * 販区コード名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHankuNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getHankuNm()
		{
			return this.hanku_nm;
		}


	/**
	 * 商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setSyohinCd(String syohin_cd)
		{
			this.syohin_cd = syohin_cd;
			return true;
		}
	/**
	 * 商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSyohinCd()
		{
			return this.syohin_cd;
		}
	/**
	 * 商品コードSAVEに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSvSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setSvSyohinCd(String sv_syohin_cd)
		{
			this.sv_syohin_cd = sv_syohin_cd;
			return true;
		}
	/**
	 * 商品コードSAVEに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSvSyohinCd()
		{
			return this.sv_syohin_cd;
		}


/**
 * 有効日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setYukoDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
/**
 * 有効日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getYukoDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getYukoDt()
	{
		return this.yuko_dt;
	}


/**
 * 漢字品名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHinmeiKanjiNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHinmeiKanjiNa(String hinmei_kanji_na)
	{
		this.hinmei_kanji_na = hinmei_kanji_na;
		return true;
	}
/**
 * 漢字品名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHinmeiKanjiNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHinmeiKanjiNa()
	{
		return this.hinmei_kanji_na;
	}


	/**
	 * 業種区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGyosyuKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setGyosyuKb(String gyosyu_kb)
		{
			this.gyosyu_kb = gyosyu_kb;
			return true;
		}
	/**
	 * 業種区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGyosyuKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getGyosyuKb()
		{
			return this.gyosyu_kb;
		}


/**
 * 大属性１に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei1Na(String d_zokusei_1_na)
	{
		this.d_zokusei_1_na = d_zokusei_1_na;
		return true;
	}
/**
 * 大属性１に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei1Na()
	{
		return this.d_zokusei_1_na;
	}


/**
 * 小属性１に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei1Na(String s_zokusei_1_na)
	{
		this.s_zokusei_1_na = s_zokusei_1_na;
		return true;
	}
/**
 * 小属性１に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei1Na()
	{
		return this.s_zokusei_1_na;
	}


/**
 * 大属性２に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei2Na(String d_zokusei_2_na)
	{
		this.d_zokusei_2_na = d_zokusei_2_na;
		return true;
	}
/**
 * 大属性２に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei2Na()
	{
		return this.d_zokusei_2_na;
	}


/**
 * 小属性２に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei2Na(String s_zokusei_2_na)
	{
		this.s_zokusei_2_na = s_zokusei_2_na;
		return true;
	}
/**
 * 小属性２に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei2Na()
	{
		return this.s_zokusei_2_na;
	}


/**
 * 大属性３に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei3Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei3Na(String d_zokusei_3_na)
	{
		this.d_zokusei_3_na = d_zokusei_3_na;
		return true;
	}
/**
 * 大属性３に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei3Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei3Na()
	{
		return this.d_zokusei_3_na;
	}


/**
 * 小属性３に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei3Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei3Na(String s_zokusei_3_na)
	{
		this.s_zokusei_3_na = s_zokusei_3_na;
		return true;
	}
/**
 * 小属性３に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei3Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei3Na()
	{
		return this.s_zokusei_3_na;
	}


/**
 * 大属性４に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei4Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei4Na(String d_zokusei_4_na)
	{
		this.d_zokusei_4_na = d_zokusei_4_na;
		return true;
	}
/**
 * 大属性４に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei4Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei4Na()
	{
		return this.d_zokusei_4_na;
	}


/**
 * 小属性４に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei4Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei4Na(String s_zokusei_4_na)
	{
		this.s_zokusei_4_na = s_zokusei_4_na;
		return true;
	}
/**
 * 小属性４に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei4Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei4Na()
	{
		return this.s_zokusei_4_na;
	}


/**
 * 大属性５に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei5Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei5Na(String d_zokusei_5_na)
	{
		this.d_zokusei_5_na = d_zokusei_5_na;
		return true;
	}
/**
 * 大属性５に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei5Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei5Na()
	{
		return this.d_zokusei_5_na;
	}


/**
 * 小属性５に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei5Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei5Na(String s_zokusei_5_na)
	{
		this.s_zokusei_5_na = s_zokusei_5_na;
		return true;
	}
/**
 * 小属性５に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei5Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei5Na()
	{
		return this.s_zokusei_5_na;
	}


/**
 * 大属性６に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei6Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei6Na(String d_zokusei_6_na)
	{
		this.d_zokusei_6_na = d_zokusei_6_na;
		return true;
	}
/**
 * 大属性６に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei6Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei6Na()
	{
		return this.d_zokusei_6_na;
	}


/**
 * 小属性６に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei6Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei6Na(String s_zokusei_6_na)
	{
		this.s_zokusei_6_na = s_zokusei_6_na;
		return true;
	}
/**
 * 小属性６に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei6Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei6Na()
	{
		return this.s_zokusei_6_na;
	}


/**
 * 大属性７に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei7Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei7Na(String d_zokusei_7_na)
	{
		this.d_zokusei_7_na = d_zokusei_7_na;
		return true;
	}
/**
 * 大属性７に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei7Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei7Na()
	{
		return this.d_zokusei_7_na;
	}


/**
 * 小属性７に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei7Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei7Na(String s_zokusei_7_na)
	{
		this.s_zokusei_7_na = s_zokusei_7_na;
		return true;
	}
/**
 * 小属性７に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei7Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei7Na()
	{
		return this.s_zokusei_7_na;
	}


/**
 * 大属性８に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei8Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei8Na(String d_zokusei_8_na)
	{
		this.d_zokusei_8_na = d_zokusei_8_na;
		return true;
	}
/**
 * 大属性８に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei8Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei8Na()
	{
		return this.d_zokusei_8_na;
	}


/**
 * 小属性８に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei8Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei8Na(String s_zokusei_8_na)
	{
		this.s_zokusei_8_na = s_zokusei_8_na;
		return true;
	}
/**
 * 小属性８に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei8Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei8Na()
	{
		return this.s_zokusei_8_na;
	}


/**
 * 大属性９に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei9Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei9Na(String d_zokusei_9_na)
	{
		this.d_zokusei_9_na = d_zokusei_9_na;
		return true;
	}
/**
 * 大属性９に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei9Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei9Na()
	{
		return this.d_zokusei_9_na;
	}


/**
 * 小属性９に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei9Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei9Na(String s_zokusei_9_na)
	{
		this.s_zokusei_9_na = s_zokusei_9_na;
		return true;
	}
/**
 * 小属性９に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei9Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei9Na()
	{
		return this.s_zokusei_9_na;
	}


/**
 * 大属性１０に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDZokusei10Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDZokusei10Na(String d_zokusei_10_na)
	{
		this.d_zokusei_10_na = d_zokusei_10_na;
		return true;
	}
/**
 * 大属性１０に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDZokusei10Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDZokusei10Na()
	{
		return this.d_zokusei_10_na;
	}


/**
 * 小属性１０に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSZokusei10Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSZokusei10Na(String s_zokusei_10_na)
	{
		this.s_zokusei_10_na = s_zokusei_10_na;
		return true;
	}
/**
 * 小属性１０に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSZokusei10Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSZokusei10Na()
	{
		return this.s_zokusei_10_na;
	}


/**
 * 作成年月日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertTs("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
/**
 * 作成年月日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertTs();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertTs()
	{
		return this.insert_ts;
	}


/**
 * 作成者社員IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertUserId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertUserId(String insert_user_id)
	{
		this.insert_user_id = insert_user_id;
		return true;
	}
/**
 * 作成者社員IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertUserId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertUserId()
	{
		return this.insert_user_id;
	}


/**
 * 更新年月日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateTs("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
/**
 * 更新年月日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateTs();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateTs()
	{
		return this.update_ts;
	}


/**
 * 更新者社員IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateUserId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateUserId(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
/**
 * 更新者社員IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateUserId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateUserId()
	{
		return this.update_user_id;
	}


/**
 * 削除フラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteFg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFg(String delete_fg)
	{
		this.delete_fg = delete_fg;
		return true;
	}
/**
 * 削除フラグに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDeleteFg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDeleteFg()
	{
		return this.delete_fg;
	}


/**
 * 処理状況に対するセッター<br>
 * <br>
 * Ex)<br>
 * setProcessingdivision("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setProcessingDivision(String processingdivision)
	{
		this.processingdivision = processingdivision;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getProcessingdivision();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getProcessingDivision()
	{
		return this.processingdivision;
	}


/**
 * エラーフラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorFlg(String errorflg)
	{
		this.errorflg = errorflg;
		return true;
	}
/**
 * エラーフラグに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorFlg()
	{
		return this.errorflg;
	}


/**
 * エラーメッセージに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrormessage("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorMessage(String errormessage)
	{
		this.errormessage = errormessage;
		return true;
	}
/**
 * エラーメッセージに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrormessage();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorMessage()
	{
		return this.errormessage;
	}


/**
 * メニューバーアイテムに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMenubar("String[]");<br>
 * <br>
 * @param String[] 設定する文字配列
 */
	public boolean setMenuBar(String[] menubar)
	{
		try
		{
			this.menubar = menubar;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * メニューバーアイテムに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMenubar();　戻り値　文字配列<br>
 * <br>
 * @return String[] 文字配列
 */
	public String[] getMenuBar()
	{
		return this.menubar;
	}


/**
 * 処理モードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("Map");<br>
 * <br>
 * @param Map 設定するMap配列
 */
	public boolean setMode(Map mode)
	{
		try
		{
			this.mode = mode;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 処理モードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMode();　戻り値　Map配列<br>
 * <br>
 * @return String[] Map配列
 */
	public Map getMode()
	{
		return this.mode;
	}


/**
 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFirstfocus("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFirstFocus(String firstfocus)
	{
		this.firstfocus = firstfocus;
		return true;
	}
/**
 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFirstfocus();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFirstFocus()
	{
		return this.firstfocus;
	}


/**
 * 新規処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertFlg(String insertflg)
	{
		this.insertflg = insertflg;
		return true;
	}
/**
 * 新規処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertFlg()
	{
		return this.insertflg;
	}


/**
 * 更新処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateFlg(String updateflg)
	{
		this.updateflg = updateflg;
		return true;
	}
/**
 * 更新処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateFlg()
	{
		return this.updateflg;
	}


/**
 * 削除処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFlg(String deleteflg)
	{
		this.deleteflg = deleteflg;
		return true;
	}
/**
 * 削除処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDeleteflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDeleteFlg()
	{
		return this.deleteflg;
	}


/**
 * 照会処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setReferenceflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setReferenceFlg(String referenceflg)
	{
		this.referenceflg = referenceflg;
		return true;
	}
/**
 * 照会処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getReferenceflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getReferenceFlg()
	{
		return this.referenceflg;
	}


/**
 * CSV処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCsvflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCsvFlg(String csvflg)
	{
		this.csvflg = csvflg;
		return true;
	}
/**
 * CSV処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCsvflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCsvFlg()
	{
		return this.csvflg;
	}


/**
 * 印刷処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setPrintflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setPrintFlg(String printflg)
	{
		this.printflg = printflg;
		return true;
	}
/**
 * 印刷処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getPrintflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getPrintFlg()
	{
		return this.printflg;
	}


/**
 * 前画面IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setBeforeDispId("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBeforeDispId(String before_disp_id)
	{
		this.before_disp_id = before_disp_id;
		return true;
	}
/**
 * 前画面IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBeforeDispId();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBeforeDispId()
	{
		return this.before_disp_id;
	}


/**
 * 現画面URLに対するセッター<br>
 * <br>
 * Ex)<br>
 * setDispUrl("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDispUrl(String disp_url)
	{
		this.disp_url = disp_url;
		return true;
	}
/**
 * 現画面URLに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDispUrl();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDispUrl()
	{
		return this.disp_url;
	}


/**
 * チェック処理判断に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCheckflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCheckFlg(String checkflg)
	{
		this.checkflg = checkflg;
		return true;
	}
/**
 * チェック処理判断に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCheckflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCheckFlg()
	{
		return this.checkflg;
	}


/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setExistflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setExistFlg(String existflg)
	{
		this.existflg = existflg;
		return true;
	}
/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getExistflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getExistFlg()
	{
		return this.existflg;
	}


/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSearcherrorflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSearchErrorFlg(String searcherrorflg)
	{
		this.searcherrorflg = searcherrorflg;
		return true;
	}
/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSearcherrorflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSearchErrorFlg()
	{
		return this.searcherrorflg;
	}


/**
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateprocessflg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateProcessFlg(String updateprocessflg)
	{
		this.updateprocessflg = updateprocessflg;
		return true;
	}
/**
 * 更新処理内容に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateprocessflg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateProcessFlg()
	{
		return this.updateprocessflg;
	}


	/**
	 * コントロールカラーに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCtrlColor("Map");<br>
	 * <br>
	 * @param Map 設定するMap配列
	 */
		public boolean setCtrlColor(Map ctrlcolor)
		{
			try
			{
				this.ctrlcolor = ctrlcolor;
			}
			catch(Exception e)
			{
				return false;
			}
			return true;
		}
	/**
	 * コントロールカラーに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCtrlColor();　戻り値　Map配列<br>
	 * <br>
	 * @return String[] Map配列
	 */
		public Map getCtrlColor()
		{
			return this.ctrlcolor;
		}


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
	
	/**
	 * 商品有効日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinYukoDt("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohinYukoDt(String syohinyukodt) {
		this.syohinyukodt = syohinyukodt;
	}
	/**
	 * 商品有効日に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohinYukoDt();　戻り値	文字列<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public String getSyohinYukoDt() {
		return syohinyukodt;
	}
	
	/**
	 * 変更フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChangeFlg("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setChangeFlg(String changeflg) {
		this.changeflg = changeflg;
	}
	/**
	 * 変更フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChangeFlg();　戻り値	文字列<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public String getChangeFlg() {
		return changeflg;
	}
//	BUGNO-S005 2005.04.21 S.Takahashi END
//BUGNO-S052 2005.05.14 SIRIUS START
	/**
	 * 初期画面URL取得(ログ出力有り)
	 * <br>
	 * Ex)<br>
	 * getInitUrl("logHeader","logMsg") -&gt; String<br>
	 * <br>
	 * @param String logHeader
	 * @param String logMsg
	 * @return		String
	 */
	  public String getInitUrl(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
		  }
		
		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }
		
		  return	INIT_PAGE;
	  }
	  /**
	   * 初期画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getInitUrl()
	  {
		  return	INIT_PAGE;
	  }

	  /**
	   * 登録画面URL取得(ログ出力有り)
	   * <br>
	   * Ex)<br>
	   * getEditUrl("logHeader","logMsg") -&gt; String<br>
	   * <br>
	   * @param String logHeader
	   * @param String logMsg
	   * @return		String
	   */
	  public String getEditUrl(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
		  }
		
		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }
		
		  return	EDIT_PAGE;
	  }
	  /**
	   * 登録画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getInitUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getEditUrl()
	  {
		  return	EDIT_PAGE;
	  }

	  /**
	   * 照会画面URL取得(ログ出力有り)
	   * <br>
	   * Ex)<br>
	   * getViewUrl("logHeader","logMsg") -&gt; String<br>
	   * <br>
	   * @param String logHeader
	   * @param String logMsg
	   * @return		String
	   */
	  public String getViewUrl(String logHeader,String logMsg)
	  {
		  //画面メッセージと同様のログを出力
		  if(this.errorflg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		  || this.errorflg.equals("")){
			  //通常系
			  if(!this.errormessage.equals("")){
				  stcLog.getLog().info(logHeader + this.errormessage);
			  }
		  } else {
			  //エラー系
			  stcLog.getLog().error(logHeader + this.errormessage);
		  }
		
		  //処理終了ログ
		  if(!logMsg.equals("")){
			  stcLog.getLog().info(logHeader + logMsg);
		  }
		
		  return	VIEW_PAGE;
	  }
	  /**
	   * 照会画面URL取得(ログ出力なし)
	   * <br>
	   * Ex)<br>
	   * getViewUrl() -&gt; String<br>
	   * <br>
	   * @return		String
	   */
	  public String getViewUrl()
	  {
		  return	VIEW_PAGE;
	  }

	  /**
	   * 権限エラー画面URL取得(ログ出力有り)
	   * <br>
	   * Ex)<br>
	   * getKengenErr("logHeader") -&gt; String<br>
	   * <br>
	   * @param String logHeader
	   * @return		String
	   */
	  public String getKengenErr(String logHeader)
	  {
		  stcLog.getLog().error(logHeader + InfoStrings.getInstance().getInfo("49999"));
		  return KENGEN_PAGE;
	  }
//BUGNO-S052 2005.05.14 SIRIUS START

}
