/**
 * <p>タイトル	：新ＭＤシステム（マスタ管理）伝票検索画面表示データ格納用クラス</p>
 * <p>説明		：新ＭＤシステムで使用する伝票検索画面表示データ格納用クラス</p>
 * <p>著作権	： Copyright (c) 2005</p>
 * <p>会社名	： Vinculum Japan Corp.</p>
 * @author 	VJC A.Tozaki
 * @version 	1.0(2005/10/15)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import mdware.master.common.dictionary.mst000101_ConstDictionary;


public class mst910201_KeepBean
{

	private static final StcLog stcLog = StcLog.getInstance();

	private String denpyo_hakko_dt_from	= null;	// 伝票発行日(from)
	private String denpyo_hakko_dt_to		= null;	// 伝票発行日(to)
	private String uriku_cd				= null;	// 売区コード
	private String hanku_cd		 		= null;	// 販区コード
	private String uriku_na		 		= null;	// 売区名称
	private String hanku_na		 		= null;	// 販区名称
	private String siiresaki_cd	 		= null;	// 仕入先コード
	private String siiresaki_na	 		= null;	// 仕入先名称
	private String tenpo_cd	 			= null;	// 店舗コード
	private String tenpo_na	 			= null;	// 店舗名称
	private String syohin_cd	 			= null;	// 商品コード
	private String syohin_na	 			= null;	// 商品名称
	private String hinsyu_cd	 			= null;	// 品種コード
	private String hinsyu_na	 			= null;	// 品種名称
	private String nohin_dt	 			= null;	// 納品日
	private String hachu_kb 				= null;	// 発注区分
	private String sort_cd	 				= null;	// 並び順
	
	// 検索条件変更チェック用
	private String denpyo_hakko_dt_from_chk	= null;	// チェック用伝発日
	private String denpyo_hakko_dt_to_chk		= null;	// チェック用伝発日
	private String uriku_cd_chk				= null;	// チェック用売区コード
	private String hanku_cd_chk				= null;	// チェック用販区コード
	private String uriku_na_chk				= null;	// チェック用売区名称
	private String hanku_na_chk				= null;	// チェック用販区名称
	private String siiresaki_cd_chk			= null;	// チェック用仕入先コード
	private String siiresaki_na_chk			= null;	// チェック用仕入先名称
	private String tenpo_cd_chk	 			= null;	// チェック用店舗コード
	private String tenpo_na_chk	 			= null;	// チェック用店舗名称
	private String syohin_cd_chk	 			= null;	// チェック用商品コード
	private String syohin_na_chk	 			= null;	// チェック用商品名称
	private String hinsyu_cd_chk	 			= null;	// チェック用品種コード
	private String hinsyu_na_chk	 			= null;	// チェック用品種名称
	private String nohin_dt_chk	 			= null;	// チェック用納品日
	private String sort_cd_chk	 				= null;	// チェック用並び順
	private String hachu_kb_chk 				= null;	// チェック用発注区分
	

	private List meisai = new ArrayList(); //一覧の明細
	private String CurrentPageNo	= "";   // 現在表示ページ
	private String LastPageNo		= "";   // 最終ページ
	private String MaxRows			= "";   // 最大行数
	private String EndRowInPage	= "";   // 現在ページの終了行
	private String StartRowInPage	= "";   // 現在ページの開始行


	private String errorFlg = null;	//エラーフラグ
	private String errorMessage = null;	//エラーメッセージ
	private String[] menuBar = null;	//メニューバーアイテム
	private String mode = null;	//処理モード
	private String firstFocus = null;	//フォーカスを最初に取得するオブジェクト名
	private String insertFlg = null;	//新規処理利用可能区分
	private String updateFlg = null;	//更新処理利用可能区分
	private String deleteFlg = null;	//削除処理利用可能区分
	private String referenceFlg = null;	//照会処理利用可能区分
	private String csvFlg = null;	//CSV処理利用可能区分
	private String printFlg = null;	//印刷処理利用可能区分
	private String before_disp_id = null;	//前画面ID
	private String disp_url = null;	//現画面URL
	private String checkFlg = null;	//チェック処理判断
	private String existFlg = null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg = null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg = null;	//更新処理内容
	private Map ctrlColor = new HashMap();	//コントロール背景色
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ


  /**
   * 伝票発行日(from)に対するセッター<br>
   * <br>
   * Ex)<br>
   * setDenpyoHakkoDtFrom("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setDenpyoHakkoDtFrom(String string) {
	denpyo_hakko_dt_from = string;
  }

  /**
   * 伝票発行日(from)に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getDenpyoHakkoDtFrom();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getDenpyoHakkoDtFrom() {
	  return denpyo_hakko_dt_from;
  }
  
  /**
   * 伝票発行日(to)に対するセッター<br>
   * <br>
   * Ex)<br>
   * setDenpyoHakkoDtTo("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setDenpyoHakkoDtTo(String string) {
	denpyo_hakko_dt_to = string;
  }

  /**
   * 伝票発行日(to)に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getDenpyoHakkoDtTo();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getDenpyoHakkoDtTo() {
	  return denpyo_hakko_dt_to;
  }
  
  /**
   * 売区コードに対するセッター<br>
   * <br>
   * Ex)<br>
   * setUrikuCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setUrikuCd(String string) {
	  uriku_cd = string;
  }

  /**
   * 売区コードに対するゲッター<br>
   * <br>
   * Ex)<br>
   * getUrikuCd();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getUrikuCd() {
	  return uriku_cd;
  }
  
  /**
   * 売区名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setUrikuNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setUrikuNa(String string) {
	  uriku_na = string;
  }

  /**
   * 売区名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getUrikuNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getUrikuNa() {
	  return uriku_na;
  }
  

  /**
   * 販区コードに対するセッター<br>
   * <br>
   * Ex)<br>
   * setHankuCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHankuCd(String string) {
	  hanku_cd = string;
  }

  /**
   * 販区コードに対するゲッター<br>
   * <br>
   * Ex)<br>
   * getHankuCd();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHankuCd() {
	  return hanku_cd;
  }
  
  /**
   * 販区名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setHankuNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHankuNa(String string) {
	  hanku_na = string;
  }

  /**
   * 販区名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getHankuNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHankuNa() {
	  return hanku_na;
  }
  
  
  /**
   * 仕入先コードに対するセッター<br>
   * <br>
   * Ex)<br>
   * setSiiresakiCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSiiresakiCd(String string) {
	  siiresaki_cd = string;
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
   * 仕入先名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setSiiresakiNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSiiresakiNa(String string) {
	  siiresaki_na = string;
  }

  /**
   * 仕入先名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSiiresakiNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSiiresakiNa() {
	  return siiresaki_na;
  }
  
  /**
   * 商品コードに対するセッター<br>
   * <br>
   * Ex)<br>
   * setSyohinCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSyohinCd(String string) {
	  syohin_cd = string;
  }

  /**
   * 商品コードに対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSyohinCd();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSyohinCd() {
	  return syohin_cd;
  }


  /**
   * 商品名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setSyohinNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSyohinNa(String string) {
	  syohin_na = string;
  }

  /**
   * 商品名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSyohinNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSyohinNa() {
	  return syohin_na;
  }



  /**
   * 品種に対するセッター<br>
   * <br>
   * Ex)<br>
   * setHinsyuCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHinsyuCd(String string) {
	  hinsyu_cd = string;
  }

  /**
   * 品種に対するゲッター<br>
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
   * 品種名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setHinsyuNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHinsyuNa(String string) {
	  hinsyu_na = string;
  }

  /**
   * 品種名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getHinsyuNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHinsyuNa() {
	  return hinsyu_na;
  }



  /**
   * 納品日に対するセッター<br>
   * <br>
   * Ex)<br>
   * setNohinDt("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setNohinDt(String string) {
		nohin_dt = string;
  }

  /**
   * 納品に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getNohinDt();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getNohinDt() {
	  return nohin_dt;
  }
  
  /**
   * 店舗コードに対するセッター<br>
   * <br>
   * Ex)<br>
   * setTenpoCd("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setTenpoCd(String string) {
		tenpo_cd = string;
  }

  /**
   * 店舗コードに対するゲッター<br>
   * <br>
   * Ex)<br>
   * getTenpoCd();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getTenpoCd() {
	  return tenpo_cd;
  }

  /**
   * 店舗名称に対するセッター<br>
   * <br>
   * Ex)<br>
   * setTenpoNa("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setTenpoNa(String string) {
		tenpo_na = string;
  }

  /**
   * 店舗名称に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getTenpoNa();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getTenpoNa() {
	  return tenpo_na;
  }


  /**
   * 発注区分に対するセッター<br>
   * <br>
   * Ex)<br>
   * setHachuKb("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHachuKb(String string) {
		hachu_kb = string;
  }

  /**
   * 発注区分に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getHachuKb();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHachuKb() {
	  return hachu_kb;
  } 


  /**
   * 並び順に対するセッター<br>
   * <br>
   * Ex)<br>
   * setSort("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSort(String string) {
		sort_cd = string;
  }

  /**
   * 並び順に対するゲッター<br>
   * <br>
   * Ex)<br>
   * getSort();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSort() {
	  return sort_cd;
  } 
   
  /**
   * 伝票発行日(from)に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setDenpyoHakkoDtFromChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setDenpyoHakkoDtFromChk(String string) {
	  denpyo_hakko_dt_from_chk = string;
  }

  /**
   * 伝票発行日(to)に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getDenpyoHakkoDtFromChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getDenpyoHakkoDtFromChk() {
	  return denpyo_hakko_dt_from_chk;
  }
  
  /**
  * 伝票発行日(from)に対するセッター（検索条件チェック用）<br>
  * <br>
  * Ex)<br>
  * setDenpyoHakkoDtToChk("文字列");<br>
  * <br>
  * @param String 設定する文字列
  */
 public void setDenpyoHakkoDtToChk(String string) {
	denpyo_hakko_dt_to_chk = string;
 }

 /**
  * 伝票発行日(to)に対するゲッター（検索条件チェック用）<br>
  * <br>
  * Ex)<br>
  * getDenpyoHakkoDtToChk();　戻り値　文字列<br>
  * <br>
  * @return String 文字列
  */
 public String getDenpyoHakkoDtToChk() {
	 return denpyo_hakko_dt_to_chk;
 }
  
 /**
   * 売区コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setUrikuCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setUrikuCdChk(String string) {
	  uriku_cd_chk = string;
  }

  /**
   * 売区コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getUrikuCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getUrikuCdChk() {
	  return uriku_cd_chk;
  }
  
  /**
   * 売区名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setUrikuNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setUrikuNaChk(String string) {
	  uriku_na_chk = string;
  }

  /**
   * 売区名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getUrikuNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getUrikuNaChk() {
	  return uriku_na_chk;
  }
  
  /**
   * 販区コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setHankuCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHankuCdChk(String string) {
	  hanku_cd_chk = string;
  }

  /**
   * 販区コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getHankuCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHankuCdChk() {
	  return hanku_cd_chk;
  }
  
  /**
   * 販区名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setHankuNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHankuNaChk(String string) {
	  hanku_na_chk = string;
  }

  /**
   * 販区名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getHankuNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHankuNaChk() {
	  return hanku_na_chk;
  }

  /**
   * 仕入先コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setSiiresakiCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSiiresakiCdChk(String string) {
	  siiresaki_cd_chk = string;
  }

  /**
   * 仕入先コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getSiiresakiCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSiiresakiCdChk() {
	  return siiresaki_cd_chk;
  }
  
  /**
   * 仕入先名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setSiiresakiNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSiiresakiNaChk(String string) {
	  siiresaki_na_chk = string;
  }

  /**
   * 仕入先名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getSiiresakiNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSiiresakiNaChk() {
	  return siiresaki_na_chk;
  }

  /**
   * 商品コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setSyohinCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSyohinCdChk(String string) {
	  syohin_cd_chk = string;
  }

  /**
   * 商品コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getSyohinCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSyohinCdChk() {
	  return syohin_cd_chk;
  }
  
  /**
   * 商品名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setSyohinNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSyohinNaChk(String string) {
	  syohin_na_chk = string;
  }

  /**
   * 商品名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getSyohinNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSyohinNaChk() {
	  return syohin_na_chk;
  }

  /**
   * 品種コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setHinsyuCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHinsyuCdChk(String string) {
	  hinsyu_cd_chk = string;
  }

  /**
   * 品種コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getHinsyuCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHinsyuCdChk() {
	  return hinsyu_cd_chk;
  }


  /**
   * 品種名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setHinsyuNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHinsyuNaChk(String string) {
	  hinsyu_na_chk = string;
  }

  /**
   * 品種名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getHinsyuNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHinsyuNaChk() {
	  return hinsyu_na_chk;
  }


  /**
   * 納品日に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setNohinDtChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setNohinDtChk(String string) {
	  nohin_dt_chk = string;
  }

  /**
   * 納品日に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getNohinDtChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getNohinDtChk() {
	  return nohin_dt_chk;
  }
  

  /**
   * 店舗コードに対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setTenpoCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setTenpoCdChk(String string) {
	  tenpo_cd_chk = string;
  }

  /**
   * 店舗コードに対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getTenpoCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getTenpoCdChk() {
	  return tenpo_cd_chk;
  }
  
  /**
   * 店舗名称に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setTenpoNaChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setTenpoNaChk(String string) {
	  tenpo_na_chk = string;
  }

  /**
   * 店舗名称に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getTenpoNaChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getTenpoNaChk() {
	  return tenpo_na_chk;
  }

  /**
   * 発注区分に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setHachuKbChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setHachuKbChk(String string) {
	  hachu_kb_chk = string;
  }

  /**
   * 発注区分に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getHachuKbChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getHachuKbChk() {
	  return hachu_kb_chk;
  }


  /**
   * 並び順に対するセッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * setSortCdChk("文字列");<br>
   * <br>
   * @param String 設定する文字列
   */
  public void setSortCdChk(String string) {
	  sort_cd_chk = string;
  }

  /**
   * 並び順に対するゲッター（検索条件チェック用）<br>
   * <br>
   * Ex)<br>
   * getSortCdChk();　戻り値　文字列<br>
   * <br>
   * @return String 文字列
   */
  public String getSortCdChk() {
	  return sort_cd_chk;
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
	 * 現在表示ページに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCurrentPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getCurrentPageNo() {
		return CurrentPageNo;
	}

	/**
	 * 現在ページの終了行に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getEndRowInPage();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getEndRowInPage() {
		return EndRowInPage;
	}

	/**
	 * 最終ページに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getLastPageNo() {
		return LastPageNo;
	}

	/**
	 * MaxRowsに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getMaxRows() {
		return MaxRows;
	}

	/**
	 * 現在ページの開始行に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getLastPageNo();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getStartRowInPage() {
		return StartRowInPage;
	}

	/**
	 * 現在表示ページに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setCurrentPageNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setCurrentPageNo(String string) {
		CurrentPageNo = string;
	}

	/**
	 * 現在ページの終了行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setEndRowInPage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setEndRowInPage(String string) {
		EndRowInPage = string;
	}

	/**
	 * 最終ページの終了行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setLastPageNo("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setLastPageNo(String string) {
		LastPageNo = string;
	}

	/**
	 * 最大行数に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setMaxRows("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setMaxRows(String string) {
		MaxRows = string;
	}

	/**
	 * 現在ページの開始行に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setStartRowInPage("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public void setStartRowInPage(String string) {
		StartRowInPage = string;
	}

}
