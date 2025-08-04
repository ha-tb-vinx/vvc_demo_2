/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
// BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
// BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/03/20)初版作成
 */
public class mst300301_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
// 	private String hanku_cd = null;	// 販区コード
// 	private String hanku_nm = null;	// 販区名
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
	private String syohin_cd = null;	// 商品コード
	private String syohin_nm = null;	// 商品名
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
	private String bumon_cd = null;	// 部門コード
	private String bumon_nm = null;// 部門名
	private String hinban_cd = null;	// 品番コード
	private String hinban_nm = null;	// 品番名
	private String hinsyu_cd = null;	// 品種コード
	private String hinsyu_nm = null;	// 品種名
	private String line_cd = null;	// ラインコード
	private String line_nm = null;	// ライン名
	private String unit_cd = null;	// ユニットコード
	private String unit_nm = null;// ユニット名
	private String siiresaki_cd = null;	// 仕入先コード
	private String siiresaki_nm = null;	// 仕入先名
	private String baitanka_vl_fm = null;	// 売単価範囲（開始）
	private String baitanka_vl_to = null;	// 売単価範囲（終了）
	private String tanawari_bangou_fm = null;// 棚割番号（開始）
	private String tanawari_bangou_to = null;// 棚割番号（終了）
	private String siiresaki_syohin_cd = null;	// 仕入先商品コード
	private String by_no = null;	// BY NO.
	private String gyotai_kb = null;	// 業態区分
	private String system_kb = null;	// システム区分
	private String back_kb = null;	// 遷移区分
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
	
	private String tenpo_cd = null;	// 店舗コード (FK)
	private String yuko_dt = null;	// 有効日
	private String hanbai_st_dt = null;	// 販売開始日
	private String non_act_kb = null;	// NON_ACT区分
	private String haseimoto_kb = null;	// 発生元区分
	private String tanawari_patern = null;	// 棚割パターン
	private long juki_nb = 0;	// 什器NO
	private long tana_nb = 0;	// 棚NO
	private long face_qt = 0;	// フェイス数
	private long min_tinretu_qt = 0;	// 最低陳列数
	private long max_tinretu_qt = 0;	// 最大陳列数
	private long base_zaiko_nisu_qt = 0;	// 基準在庫日数
	private String tengroupno_nm = null;	//店配列名称 add by kema 06.11.08
	private String insert_ts = null;	// 作成年月日
	private String insert_user_id = null;	// 作成者社員ID
	private String update_ts = null;	// 更新年月日
	private String update_user_id = null;	// 更新者社員ID
	//===BEGIN=== Add by kou 2006/11/14
	private String sscdFlg = null;		//あいまい検索フラグ
	//===END=== Add by kou 2006/11/14
	
	private String ChangeFlg			= "0";				// 明細部変更かどうか判断
	private String delete_fg = null;	// 削除フラグ
	private String processingDivision = null;	// 処理状況
	private String errorFlg = null;	// エラーフラグ
	private String errorMessage = null;	// エラーメッセージ
	private String[] menuBar = null;	// メニューバーアイテム
	private String mode = null;	// 処理モード
	private String firstFocus = null;	// フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;	// 新規処理利用可能区分
	private String updateFlg = null;	// 更新処理利用可能区分
	private String deleteFlg = null;	// 削除処理利用可能区分
	private String referenceFlg = null;	// 照会処理利用可能区分
	private String csvFlg = null;	// CSV処理利用可能区分
	private String printFlg = null;	// 印刷処理利用可能区分
	private String before_disp_id = null;	// 前画面ID
	private String disp_url = null;	// 現画面URL
	private String checkFlg = null;	// チェック処理判断
	private String existFlg = null;	// データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null;	// エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	// 更新処理内容
	private Map ctrlColor = new HashMap();	// コントロール背景色
	private List meisai = new ArrayList();
	private int currentPageNum = 0;// 表示ページ番号
	private int lastPageNum = 0;// 表示ページ番号
	private int maxRows = 0;
	private int startRowInPage = 0;
	private int endRowInPage = 0;
	private int maxRowInPage = 0;// 1ページの最大表示件数
	private int cnt = 0;// 更新件数
	private String chflg = null;
	private boolean eflg = true;

// BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst300101_TanpinTenToriatukaiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst300201_TanpinTenToriatukaiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst300301_TanpinTenToriatukaiView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";			// 権限エラーJSPを取得
// BUGNO-S052 2005.05.14 Y.Gotoh END

//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓
// 	/**
// 	 * 販区コードに対するセッター<br>
// 	 * <br>
// 	 * Ex)<br>
// 	 * setHankuCd("文字列");<br>
// 	 * <br>
// 	 * @param String 設定する文字列
// 	 */
// 		public boolean setHankuCd(String hanku_cd)
// 		{
// 			this.hanku_cd = hanku_cd;
// 			return true;
// 		}
// 	/**
// 	 * 販区コードに対するゲッター<br>
// 	 * <br>
// 	 * Ex)<br>
// 	 * getHankuCd();　戻り値　文字列<br>
// 	 * <br>
// 	 * @return String 文字列
// 	 */
// 		public String getHankuCd()
// 		{
// 			return this.hanku_cd;
// 		}
// 
// 		/**
// 		 * 販区名に対するゲッター<br>
// 		 * <br>
// 		 * Ex)<br>
// 		 * getHanku_nm();　戻り値　文字列<br>
// 		 * <br>
// 		 * @return String 文字列
// 		 */
// 		public String getHanku_nm() {
// 			return hanku_nm;
// 		}
// 
// 		/**
// 		 * 販区名に対するセッター<br>
// 		 * <br>
// 		 * Ex)<br>
// 		 * setHanku_nm("文字列");<br>
// 		 * <br>
// 		 * @param String 設定する文字列
// 		 */
// 		public void setHanku_nm(String string) {
// 			hanku_nm = string;
// 		}
//      ↑↑2006.06.21 fanglh カスタマイズ修正↑↑
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
	 * 商品名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSyohin_nm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSyohin_nm() {
		return syohin_nm;
	}

	/**
	 * 商品名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSyohin_nm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSyohin_nm(String string) {
		syohin_nm = string;
	}

/**
 * 店舗コード (FK)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTenpoCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
/**
 * 店舗コード (FK)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTenpoCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTenpoCd()
	{
		return this.tenpo_cd;
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
 * 販売開始日に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHanbaiStDt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHanbaiStDt(String hanbai_st_dt)
	{
		this.hanbai_st_dt = hanbai_st_dt;
		return true;
	}
/**
 * 販売開始日に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHanbaiStDt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHanbaiStDt()
	{
		return this.hanbai_st_dt;
	}


/**
 * NON_ACT区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setNonActKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setNonActKb(String non_act_kb)
	{
		this.non_act_kb = non_act_kb;
		return true;
	}
/**
 * NON_ACT区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getNonActKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getNonActKb()
	{
		return this.non_act_kb;
	}


/**
 * 発生元区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHaseimotoKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHaseimotoKb(String haseimoto_kb)
	{
		this.haseimoto_kb = haseimoto_kb;
		return true;
	}
/**
 * 発生元区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHaseimotoKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHaseimotoKb()
	{
		return this.haseimoto_kb;
	}


/**
 * 棚割パターンに対するセッター<br>
 * <br>
 * Ex)<br>
 * setTanawariPatern("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTanawariPatern(String tanawari_patern)
	{
		this.tanawari_patern = tanawari_patern;
		return true;
	}
/**
 * 棚割パターンに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTanawariPatern();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTanawariPatern()
	{
		return this.tanawari_patern;
	}


/**
 * 什器NOに対するセッター<br>
 * <br>
 * Ex)<br>
 * setJukiNb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setJukiNb(String juki_nb)
	{
		try
		{
			this.juki_nb = Long.parseLong(juki_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 什器NOに対するセッター<br>
 * <br>
 * Ex)<br>
 * setJukiNb("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setJukiNb(long juki_nb)
	{
		this.juki_nb = juki_nb;
		return true;
	}
	public long getJukiNb()
	{
		return this.juki_nb;
	}
/**
 * 什器NOに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getJukiNb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getJukiNbString()
	{
		return Long.toString(this.juki_nb);
	}


/**
 * 棚NOに対するセッター<br>
 * <br>
 * Ex)<br>
 * setTanaNb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTanaNb(String tana_nb)
	{
		try
		{
			this.tana_nb = Long.parseLong(tana_nb);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 棚NOに対するセッター<br>
 * <br>
 * Ex)<br>
 * setTanaNb("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setTanaNb(long tana_nb)
	{
		this.tana_nb = tana_nb;
		return true;
	}
	public long getTanaNb()
	{
		return this.tana_nb;
	}
/**
 * 棚NOに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTanaNb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTanaNbString()
	{
		return Long.toString(this.tana_nb);
	}


/**
 * フェイス数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFaceQt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFaceQt(String face_qt)
	{
		try
		{
			this.face_qt = Long.parseLong(face_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * フェイス数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFaceQt("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setFaceQt(long face_qt)
	{
		this.face_qt = face_qt;
		return true;
	}
	public long getFaceQt()
	{
		return this.face_qt;
	}
/**
 * フェイス数に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFaceQt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFaceQtString()
	{
		return Long.toString(this.face_qt);
	}


/**
 * 最低陳列数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMinTinretuQt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMinTinretuQt(String min_tinretu_qt)
	{
		try
		{
			this.min_tinretu_qt = Long.parseLong(min_tinretu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 最低陳列数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMinTinretuQt("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setMinTinretuQt(long min_tinretu_qt)
	{
		this.min_tinretu_qt = min_tinretu_qt;
		return true;
	}
	public long getMinTinretuQt()
	{
		return this.min_tinretu_qt;
	}
/**
 * 最低陳列数に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMinTinretuQt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMinTinretuQtString()
	{
		return Long.toString(this.min_tinretu_qt);
	}


/**
 * 最大陳列数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMaxTinretuQt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMaxTinretuQt(String max_tinretu_qt)
	{
		try
		{
			this.max_tinretu_qt = Long.parseLong(max_tinretu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 最大陳列数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMaxTinretuQt("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setMaxTinretuQt(long max_tinretu_qt)
	{
		this.max_tinretu_qt = max_tinretu_qt;
		return true;
	}
	public long getMaxTinretuQt()
	{
		return this.max_tinretu_qt;
	}
/**
 * 最大陳列数に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMaxTinretuQt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMaxTinretuQtString()
	{
		return Long.toString(this.max_tinretu_qt);
	}


/**
 * 基準在庫日数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setBaseZaikoNisuQt("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setBaseZaikoNisuQt(String base_zaiko_nisu_qt)
	{
		try
		{
			this.base_zaiko_nisu_qt = Long.parseLong(base_zaiko_nisu_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * 基準在庫日数に対するセッター<br>
 * <br>
 * Ex)<br>
 * setBaseZaikoNisuQt("long");<br>
 * <br>
 * @param long 設定する値
 */
	public boolean setBaseZaikoNisuQt(long base_zaiko_nisu_qt)
	{
		this.base_zaiko_nisu_qt = base_zaiko_nisu_qt;
		return true;
	}
	public long getBaseZaikoNisuQt()
	{
		return this.base_zaiko_nisu_qt;
	}
/**
 * 基準在庫日数に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getBaseZaikoNisuQt();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getBaseZaikoNisuQtString()
	{
		return Long.toString(this.base_zaiko_nisu_qt);
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
// 	******** ＤＢ Ver3.6修正箇所 *****************************************
/**
 * 更新者社員IDに対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateUserTs("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	

/**
 * 更新者社員IDに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateUserTs();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateUserTs()
	{
		return this.update_user_id;
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
 * setProcessingDivision("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setProcessingDivision(String processingDivision)
	{
		this.processingDivision = processingDivision;
		return true;
	}
/**
 * 処理状況に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getProcessingDivision();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getProcessingDivision()
	{
		return this.processingDivision;
	}


/**
 * エラーフラグに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorFlg(String errorFlg)
	{
		this.errorFlg = errorFlg;
		return true;
	}
/**
 * エラーフラグに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorFlg()
	{
		return this.errorFlg;
	}

	// ChangeFlgに対するセッターとゲッターの集合
	public boolean setChangeFlg(String ChangeFlg)
	{
		this.ChangeFlg = ChangeFlg;
		return true;
	}
	public String getChangeFlg()
	{
		return this.ChangeFlg;
	}
	
/**
 * エラーメッセージに対するセッター<br>
 * <br>
 * Ex)<br>
 * setErrorMessage("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
		return true;
	}
/**
 * エラーメッセージに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getErrorMessage();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getErrorMessage()
	{
		return this.errorMessage;
	}


/**
 * メニューバーアイテムに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMenuBar("String[]");<br>
 * <br>
 * @param String[] 設定する文字配列
 */
	public boolean setMenuBar(String[] menuBar)
	{
		try
		{
			this.menuBar = menuBar;
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
 * getMenuBar();　戻り値　文字配列<br>
 * <br>
 * @return String[] 文字配列
 */
	public String[] getMenuBar()
	{
		return this.menuBar;
	}


/**
 * 処理モードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setMode("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMode(String mode)
	{
		this.mode = mode;
		return true;
	}
/**
 * 処理モードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMode();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMode()
	{
		return this.mode;
	}


/**
 * フォーカスを最初に取得するオブジェクト名に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFirstFocus("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFirstFocus(String firstFocus)
	{
		this.firstFocus = firstFocus;
		return true;
	}
/**
 * フォーカスを最初に取得するオブジェクト名に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFirstFocus();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFirstFocus()
	{
		return this.firstFocus;
	}


/**
 * 新規処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setInsertFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setInsertFlg(String insertFlg)
	{
		this.insertFlg = insertFlg;
		return true;
	}
/**
 * 新規処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getInsertFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getInsertFlg()
	{
		return this.insertFlg;
	}


/**
 * 更新処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateFlg(String updateFlg)
	{
		this.updateFlg = updateFlg;
		return true;
	}
/**
 * 更新処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateFlg()
	{
		return this.updateFlg;
	}


/**
 * 削除処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setDeleteFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setDeleteFlg(String deleteFlg)
	{
		this.deleteFlg = deleteFlg;
		return true;
	}
/**
 * 削除処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getDeleteFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getDeleteFlg()
	{
		return this.deleteFlg;
	}


/**
 * 照会処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setReferenceFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setReferenceFlg(String referenceFlg)
	{
		this.referenceFlg = referenceFlg;
		return true;
	}
/**
 * 照会処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getReferenceFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getReferenceFlg()
	{
		return this.referenceFlg;
	}


/**
 * CSV処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCsvFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCsvFlg(String csvFlg)
	{
		this.csvFlg = csvFlg;
		return true;
	}
/**
 * CSV処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCsvFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCsvFlg()
	{
		return this.csvFlg;
	}


/**
 * 印刷処理利用可能区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setPrintFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setPrintFlg(String printFlg)
	{
		this.printFlg = printFlg;
		return true;
	}
/**
 * 印刷処理利用可能区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getPrintFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getPrintFlg()
	{
		return this.printFlg;
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
 * setCheckFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setCheckFlg(String checkFlg)
	{
		this.checkFlg = checkFlg;
		return true;
	}
/**
 * チェック処理判断に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCheckFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getCheckFlg()
	{
		return this.checkFlg;
	}


/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setExistFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setExistFlg(String existFlg)
	{
		this.existFlg = existFlg;
		return true;
	}
/**
 * データ存在(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getExistFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getExistFlg()
	{
		return this.existFlg;
	}


/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSearchErrorFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSearchErrorFlg(String searchErrorFlg)
	{
		this.searchErrorFlg = searchErrorFlg;
		return true;
	}
/**
 * エラーフラグ(検索[ｷｬﾝｾﾙ]時)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSearchErrorFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSearchErrorFlg()
	{
		return this.searchErrorFlg;
	}


/**
 * 更新処理内容に対するセッター<br>
 * <br>
 * Ex)<br>
 * setUpdateProcessFlg("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setUpdateProcessFlg(String updateProcessFlg)
	{
		this.updateProcessFlg = updateProcessFlg;
		return true;
	}
/**
 * 更新処理内容に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getUpdateProcessFlg();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getUpdateProcessFlg()
	{
		return this.updateProcessFlg;
	}


/**
 * コントロール背景色に対するセッター<br>
 * <br>
 * Ex)<br>
 * setCtrlColor("Map");<br>
 * <br>
 * @param Map 設定するMap配列
 */
	public boolean setCtrlColor(Map ctrlColor)
	{
		try
		{
			this.ctrlColor = ctrlColor;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
/**
 * コントロール背景色に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getCtrlColor();　戻り値　Map配列<br>
 * <br>
 * @return String[] Map配列
 */
	public Map getCtrlColor()
	{
		return this.ctrlColor;
	}

	/**
	 * 明細配列に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMeisai();　戻り値　明細配列<br>
	 * <br>
	 * @return List 明細配列
	 */
	public List getMeisai() {
		return meisai;
	}

	/**
	 * 明細配列に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMeisai(List);<br>
	 * <br>
	 * @param List 設定する明細配列
	 */
	public void setMeisai(List list) {
		meisai = list;
	}

	/**
	 * 表示ページ番号に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCurrentPageNum();　戻り値　ページ番号<br>
	 * <br>
	 * @return int ページ番号
	 */
	public int getCurrentPageNum() {
		return currentPageNum;
	}

	/**
	 * 表示ページ番号に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCurrentPageNum(int);<br>
	 * <br>
	 * @param int 設定するページ番号
	 */
	public void setCurrentPageNum(int i) {
		currentPageNum = i;
	}

	/**
	 * 最終ページ番号に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNum();　戻り値　最終ページ番号<br>
	 * <br>
	 * @return int ページ番号
	 */
	public int getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * 最終ページ番号に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLastPageNum(int);<br>
	 * <br>
	 * @param int 最終ページ番号
	 */
	public void setLastPageNum(int i) {
		lastPageNum = i;
	}

	/**
	 * 取得件数に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getMaxRows();　戻り値　取得件数<br>
	 * <br>
	 * @return int 取得件数
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * 取得件数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMaxRows(int);<br>
	 * <br>
	 * @param int 取得件数
	 */
	public void setMaxRows(int i) {
		maxRows = i;
	}

	/**
	 * 表示開始位置に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getStartRowInPage();　戻り値　表示開始位置<br>
	 * <br>
	 * @return int 表示開始位置
	 */
	public int getStartRowInPage() {
		return startRowInPage;
	}

	/**
	 * 表示開始位置に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setStartRowInPage(int);<br>
	 * <br>
	 * @param int 表示開始位置
	 */
	public void setStartRowInPage(int i) {
		startRowInPage = i;
	}

	/**
	 * 表示終了位置に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getEndRowInPage();　戻り値　表示終了位置<br>
	 * <br>
	 * @return int 表示終了位置
	 */
	public int getEndRowInPage() {
		return endRowInPage;
	}

	/**
	 * 表示終了位置に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setEndRowInPage(int);<br>
	 * <br>
	 * @param int 表示終了位置
	 */
	public void setEndRowInPage(int i) {
		endRowInPage = i;
	}

// BUGNO-S052 2005.05.14 SIRIUS START
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
		// 画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			// 通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		// 処理終了ログ
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
		// 画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			// 通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		// 処理終了ログ
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
		// 画面メッセージと同様のログを出力
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			// 通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			// エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
		}
		
		// 処理終了ログ
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
// BUGNO-S052 2005.05.14 SIRIUS START
	
//      ↓↓2006.06.21 fanglh カスタマイズ修正↓↓

	/**
	 * 売単価範囲（開始）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBaitankaVlFm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBaitankaVlFm() {
		return baitanka_vl_fm;
	}
	
	/**
	 * 売単価範囲（開始）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBaitankaVlFm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setBaitankaVlFm(String baitanka_vl_fm) {
		this.baitanka_vl_fm = baitanka_vl_fm;
	}

	/**
	 * 売単価範囲（終了）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBaitankaVlTo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getBaitankaVlTo() {
		return baitanka_vl_to;
	}
	
	/**
	 * 売単価範囲（終了）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBaitankaVlTo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setBaitankaVlTo(String baitanka_vl_to) {
		this.baitanka_vl_to = baitanka_vl_to;
	}

	/**
	 * BY NO.に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getByNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getByNo() {
		return by_no;
	}
	
	/**
	 * BY NO.に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setByNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setByNo(String by_no) {
		this.by_no = by_no;
	}

	/**
	 * 業態区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getGyoutaiKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGyotaiKb() {
		return gyotai_kb;
	}
	
	/**
	 * 業態区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setGyoutaiKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setGyotaiKb(String gyotai_kb) {
		this.gyotai_kb = gyotai_kb;
	}

//  ↓↓ add by kema 06.11.08
// 	店配列名称に対するゲッターセッター
	public String getTenGroupNoNm() {
		return tengroupno_nm;
	}
	public void setTenGroupNoNm(String tengroupno_nm) {
		this.tengroupno_nm = tengroupno_nm;
	}
//	↑↑ add by kema 06.11.08

	/**
	 * // 品番コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinbanCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinbanCd() {
		return hinban_cd;
	}
	
	/**
	 * // 品番コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinbanCd(String hinban_cd) {
		this.hinban_cd = hinban_cd;
	}

	/**
	 * 品番名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinbanNm() {
		return hinban_nm;
	}
	
	/**
	 * 品番名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinbanNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinbanNm(String hinban_nm) {
		this.hinban_nm = hinban_nm;
	}

	/**
	 * 品種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinsyuCd() {
		return hinsyu_cd;
	}
	
	/**
	 * 品種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinsyuCd(String hinsyu_cd) {
		this.hinsyu_cd = hinsyu_cd;
	}

	/**
	 * 品種名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getHinsyuNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getHinsyuNm() {
		return hinsyu_nm;
	}
	
	/**
	 * 品種名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setHinsyuNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setHinsyuNm(String hinsyu_nm) {
		this.hinsyu_nm = hinsyu_nm;
	}

	/**
	 * ラインコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLineCd() {
		return line_cd;
	}
	
	/**
	 * ラインコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLineCd(String line_cd) {
		this.line_cd = line_cd;
	}

	/**
	 * ライン名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLineNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLineNm() {
		return line_nm;
	}
	
	/**
	 * ライン名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLineNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLineNm(String line_nm) {
		this.line_nm = line_nm;
	}

	/**
	 * 仕入先コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiCd() {
		return siiresaki_cd;
	}
	
	/**
	 * 仕入先コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiCd(String siiresaki_cd) {
		this.siiresaki_cd = siiresaki_cd;
	}

	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiNm() {
		return siiresaki_nm;
	}
	
	/**
	 * 仕入先名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiNm(String siiresaki_nm) {
		this.siiresaki_nm = siiresaki_nm;
	}

	/**
	 * 仕入先商品コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiSyohinCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSiiresakiSyohinCd() {
		return siiresaki_syohin_cd;
	}
	
	/**
	 * 仕入先商品コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSiiresakiSyohinCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSiiresakiSyohinCd(String siiresaki_syohin_cd) {
		this.siiresaki_syohin_cd = siiresaki_syohin_cd;
	}

	/**
	 * 棚割番号（開始）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTanawariBangouFm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTanawariBangouFm() {
		return tanawari_bangou_fm;
	}
	
	/**
	 * 棚割番号（開始）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTanawariBangouFm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTanawariBangouFm(String tanawari_bangou_fm) {
		this.tanawari_bangou_fm = tanawari_bangou_fm;
	}

	/**
	 * 棚割番号（終了）に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getTanawariBangouTo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getTanawariBangouTo() {
		return tanawari_bangou_to;
	}
	
	/**
	 * 棚割番号（終了）に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setTanawariBangouTo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setTanawariBangouTo(String tanawari_bangou_to) {
		this.tanawari_bangou_to = tanawari_bangou_to;
	}

	/**
	 * ユニットコードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUnitCd() {
		return unit_cd;
	}
	
	/**
	 * ユニットコードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUnitCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setUnitCd(String unit_cd) {
		this.unit_cd = unit_cd;
	}

	/**
	 * ユニット名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getUnitNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getUnitNm() {
		return unit_nm;
	}
	
	/**
	 * ユニット名に対するセッター<br>システム区分
	 * <br>
	 * Ex)<br>
	 * setUnitNm("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setUnitNm(String unit_nm) {
		this.unit_nm = unit_nm;
	}
	
	/**
	 * システム区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSystemKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSystemKb() {
		return system_kb;
	}
	
	/**
	 * システム区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSystemKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setSystemKb(String system_kb) {
		this.system_kb = system_kb;
	}
	

	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setBumonCd(String bumon_cd)
		{
			this.bumon_cd = bumon_cd;
			return true;
		}
	/**
	 * 部門コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getBumonCd();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	 public String getBumonCd()
	 {
	 	return this.bumon_cd;
	 }

	 /**
	  * 部門名に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getBumon_nm();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public String getBumon_nm() {
	 	return bumon_nm;
	 }

	 /**
	  * 部門名に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setBumon_nm("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setBumon_nm(String string) {
	 	bumon_nm = string;
	 }
	 
	 /**
	  * 遷移区分に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getBumon_nm();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public String getBackKb() {
	 	return back_kb;
	 }

	 /**
	  * 遷移区分に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setBumon_nm("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setBackKb(String string) {
		 back_kb = string;
	 }
	 
	 /**
	  * 遷移区分に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getBumon_nm();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public String getChFlg() {
	 	return chflg;
	 }

	 /**
	  * 遷移区分に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setBumon_nm("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setChFlg(String string) {
		 chflg = string;
	 }
	 
	 /**
	  * エラー区分に対するゲッター<br>
	  * <br>
	  * Ex)<br>
	  * getEFlg();　戻り値　文字列<br>
	  * <br>
	  * @return String 文字列
	  */
	 public boolean getEFlg() {
	 	return eflg;
	 }

	 /**
	  * エラー区分に対するセッター<br>
	  * <br>
	  * Ex)<br>
	  * setEFlg("文字列");<br>
	  * <br>
	  * @param String 設定する文字列
	  */
	 public void setEFlg(boolean flg) {
		 eflg = flg;
	 }

	/**
	* 1ページの最大表示件数<br>
	* <br>
	* Ex)<br>
	* getMaxRowInPage();　<br>
	* <br>
	* @return int 
	*/
	public int getMaxRowInPage() {
		return maxRowInPage;
	}

	/**
	* 1ページの最大表示件数<br>
	* <br>
	* Ex)<br>
	* setMaxRowInPage(int);<br>
	* <br>
	* @param int 
	*/
	public void setMaxRowInPage(int i) {
		maxRowInPage = i;
	}

	/**
	* 更新件数<br>
	* <br>
	* Ex)<br>
	* getMaxRowInPage();　<br>
	* <br>
	* @return int 
	*/
	public int getCnt() {
		return cnt;
	}

	/**
	* 更新件数<br>
	* <br>
	* Ex)<br>
	* setMaxRowInPage(int);<br>
	* <br>
	* @param int 
	*/
	public void setCnt(int i) {
		cnt = i;
	}
	
//   ↑↑2006.06.21 fanglh カスタマイズ修正↑↑

	/**
	 * @return
	 */
	public String getSscdFlg()
	{
		return sscdFlg;
	}

	/**
	 * @param string
	 */
	public void setSscdFlg(String string)
	{
		sscdFlg = string;
	}

}
