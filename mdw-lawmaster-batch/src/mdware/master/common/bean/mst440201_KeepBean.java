/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;
import jp.co.vinculumjapan.stc.util.infostring.InfoStrings;


import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
//BUGNO-S052 2005.05.14 Y.Gotoh START
import mdware.master.common.dictionary.mst000101_ConstDictionary;
//BUGNO-S052 2005.05.14 Y.Gotoh END

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）仕入先マスタの画面表示データ格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する仕入先マスタの画面表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Takahashi
 * @version 1.0(2005/03/24)初版作成
 * @version 1.1(2006/03/29)課金種別コード　追加 JINNO
 * @version 1.2(2006/09/24,2006/09/25,2006/09/26) 障害票№0036対応 業種(POS、タグ衣料)チェックボックスを追加 K.Tanigawa
 */
public class mst440201_KeepBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	private String kanri_kb	 			= null;	//管理区分
	private String kanri_cd 				= null;	//部門コード
	private String area_cd 				= null;	//地区コード
//	private String copy_kanri_cd			= null;	//コピー管理コード (FK)
	private String copy_bumon_cd		= null;	//コピー部門コード (FK)
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	private String siiresaki_cd 			= null;	//仕入先コード

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	private String kanri_nm			= null;	//管理コード名称
//	private String copykanri_nm			= null;	//コピー管理コード名称
	private String copybumon_nm		= null;	//コピー部門コード名称
	private String flg		= null;	//コピー部門コード名称
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	private String siiresaki_nm			= null;	//仕入先名称

	private String kanji_na 				= null;	//正式名称(漢字)
	private String kana_na 				= null;	//正式名称(カナ)
	private String kanji_rn 				= null;	//略式名称(漢字)
	private String kana_rn 				= null;	//略式名称(カナ)
	private String yubin_cd 				= null;	//郵便番号
	private String address_kanji1_na 		= null;	//住所(漢字)1
	private String address_kanji2_na 		= null;	//住所(漢字)2
	private String address_kana1_na 		= null;	//住所(カナ)1
	private String address_kana2_na 		= null;	//住所(カナ)2
	private String madoguchi_tanto_na 		= null;	//窓口担当者
	private String tel1_na 				= null;	//通常連絡先TEL1
	private String tel2_na 				= null;	//通常連絡先TEL2
	private String fax1_na 				= null;	//通常連絡先FAX1
	private String fax2_na 				= null;	//通常連絡先FAX2
	private String night_tel_na 			= null;	//夜間連絡先TEL(緊急)
	private String email_na 				= null;	//E-MAIL
	private String johosyori_seikyu_kb 	= null;	//情報処理料金請求区分
	private String syhincd_print_kb 		= null;	//商品コード印字区分
	private String taghako_gyosya_kb 		= null;	//タグ発行代行業者
//	↓↓削除（2005.06.30）↓↓
//	private String hako_basyo_na 			= null;	//発行場所
//	↑↑削除（2005.06.30）↑↑
	private String hinbanbetu_denpyo_kb	= null;	//品番別伝票区分
	private String memo_tx 				= null;	//メモ欄
	private String tosan_kb 				= null;	//倒産区分
	private String insert_ts 				= null;	//作成年月日
	private String insert_user_id 			= null;	//作成者社員ID
	private String update_ts 				= null;	//更新年月日
	private String update_user_id 			= null;	//更新者社員ID
	private String delete_fg 				= null;	//削除フラグ
	
	//↓↓仕様追加による変更（2005.05.24）
	private String eos_kb					= null;	//EOS区分
	//↑↑仕様追加による変更（2005.05.24）
	
//	↓↓2006.06.23 wangzhg カスタマイズ修正↓↓
//	↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓		
//	private String kakin_syubetu_cd		= null;	//課金種別コード
//	↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
	private String asty_kb 			= null;	//アスティ区分
	private String hanbai_keiyaku_kb 	= null;	//販売データ契約区分
	private String back_kb 	= null;	// 遷移区分
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
	private String processingDivision 		= null;	//処理状況
	private String errorFlg 				= null;	//エラーフラグ
	private String errorMessage 			= null;	//エラーメッセージ
	private String[] menuBar 				= null;	//メニューバーアイテム
	private String mode 					= null;	//処理モード
	private String firstFocus 				= null;	//フォーカスを最初に取得するオブジェクト名
	private String insertFlg 				= null;	//新規処理利用可能区分
	private String updateFlg 				= null;	//更新処理利用可能区分
	private String deleteFlg 				= null;	//削除処理利用可能区分
	private String referenceFlg 			= null;	//照会処理利用可能区分
	private String csvFlg 					= null;	//CSV処理利用可能区分
	private String printFlg 				= null;	//印刷処理利用可能区分
	private String checkFlg 				= null;	//チェック処理判断
	private String existFlg 				= null;	//データ存在(検索[ｷｬﾝｾﾙ]時)
	private String searchErrorFlg 			= null;	//エラーフラグ(検索[ｷｬﾝｾﾙ]時)
	private String updateProcessFlg 		= null;	//更新処理内容
//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
	private String code_cd 		= null;	//コード(名称・CTFマスタ)
	private String name_kanji_rn 		= null;	//略式名称(漢字)(名称・CTFマスタ)
	private List astylist 		= null;    //アスティ区分
	private List hsalelist 		= null;    //販売データ契約区分
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	private Map ctrlColor 					= new HashMap();	//コントロール背景色
	private boolean seisen 				= false;	//生鮮担当者判断フラグ
//	BUGNO-S005 2005.04.22 T.Makuta START
	private String changeflg				= "0";		//変更フラグ
//	BUGNO-S005 2005.04.22 T.Makuta END	

//	 ADD by Tanigawa 2006/9/25 障害票№0036対応 タグ、POSの画面状態保持用変数宣言 START
	private boolean bPos = false;
	private boolean bTag = false;
//	 ADD by Tanigawa 2006/9/25 障害票№0036対応  END 
	
//	 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加 START
	private String copy_chiku_cd		= null;	
//	 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加  END 
	
//BUGNO-S052 2005.05.14 Y.Gotoh START
	private static final String INIT_PAGE = "mst440101_SiiresakiInit";	// 初期画面JSPを取得
	private static final String EDIT_PAGE = "mst440201_SiiresakiEdit";	// 新規・修正画面JSPを取得
	private static final String VIEW_PAGE = "mst440301_SiiresakiView";	// 照会画面JSPを取得
	private static final String KENGEN_PAGE = "mst000401_KengenError";	// 権限エラーJSPを取得
//BUGNO-S052 2005.05.14 Y.Gotoh END

//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	**
//	 * list(アスティ区分)に対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setCodeCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
		public void setAstyList(List astylist)
		{
			this.astylist = astylist;
		}
//	**
//	 * listに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getCodeCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
		public List getAstyList()
		{
			return this.astylist;
		}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		**
//		 * list(販売データ契約区分)に対するセッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * setHsaleList("文字列");<br>
//		 * <br>
//		 * @param String 設定する文字列
//		 */
			public void setHSaleList(List hsalelist)
			{
				this.hsalelist = hsalelist;
			}
//		**
//		 * list(販売データ契約区分)に対するゲッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * getHSaleList();　戻り値　文字列<br>
//		 * <br>
//		 * @return String 文字列
//		 */
			public List getHSaleList()
			{
				return this.hsalelist;
			}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
//	↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	**
//	 * コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setCodeCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
		public boolean setCodeCd(String code_cd)
		{
			this.code_cd = code_cd;
			return true;
		}
//	**
//	 * コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getCodeCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
		public String getCodeCd()
		{
			return this.code_cd;
		}
//	↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//		**
//		 * 略式名称(漢字)(名称・CTFマスタ)に対するセッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * setNameKanjiRn("文字列");<br>
//		 * <br>
//		 * @param String 設定する文字列
//		 */
			public boolean setNameKanjiRn(String name_kanji_rn)
			{
				this.name_kanji_rn = name_kanji_rn;
				return true;
			}
//		**
//		 * 略式名称(漢字)(名称・CTFマスタ)に対するゲッター<br>
//		 * <br>
//		 * Ex)<br>
//		 * getNameKanjiRn();　戻り値　文字列<br>
//		 * <br>
//		 * @return String 文字列
//		 */
			public String getNameKanjiRn()
			{
				return this.name_kanji_rn;
			}
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			
//		↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
		 /**
		  * 遷移区分に対するゲッター<br>
		  * <br>
		  * Ex)<br>
		  * getBackKb();　戻り値　文字列<br>
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
		  * setBackKb("文字列");<br>
		  * <br>
		  * @param String 設定する文字列
		  */
		 public void setBackKb(String string) {
			 back_kb = string;
		 }
//		↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//**
// * 管理区分に対するセッター<br>
// * <br>
// * Ex)<br>
// * setKanriKb("文字列");<br>
// * <br>
// * @param String 設定する文字列
// */
//	public boolean setKanriKb(String kanri_kb)
//	{
//		this.kanri_kb = kanri_kb;
//		return true;
//	}
//**
// * 管理区分に対するゲッター<br>
// * <br>
// * Ex)<br>
// * getKanriKb();　戻り値　文字列<br>
// * <br>
// * @return String 文字列
// */
//	public String getKanriKb()
//	{
//		return this.kanri_kb;
//	}
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	/**
//	 * 管理コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setKanriCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setKanriCd(String kanri_cd)
//		{
//			this.kanri_cd = kanri_cd;
//			return true;
//		}
//	/**
//	 * 管理コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getKanriCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getKanriCd()
//		{
//			return this.kanri_cd;
//		}
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
	/**
	 * 部門コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setBumonCd("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setBumonCd(String kanri_cd)
		{
			this.kanri_cd = kanri_cd;
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
			return this.kanri_cd;
		}
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑


//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
		/**
		 * 地区コードに対するセッター<br>
		 * <br>
		 * Ex)<br>
		 * setTikuCd("文字列");<br>
		 * <br>
		 * @param String 設定する文字列
		 */
			public boolean setTikuCd(String area_cd)
			{
				this.area_cd = area_cd;
				return true;
			}
		/**
		 * 地区コードに対するゲッター<br>
		 * <br>
		 * Ex)<br>
		 * getKanriCd();　戻り値　文字列<br>
		 * <br>
		 * @return String 文字列
		 */
			public String getTikuCd()
			{
				return this.area_cd;
			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	/**
//	 * コピー管理コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setCopyKanriCd("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setCopyKanriCd(String copy_kanri_cd)
//		{
//			this.copy_kanri_cd = copy_kanri_cd;
//			return true;
//		}
//	/**
//	 * コピー管理コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getCopyKanriCd();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getCopyKanriCd()
//		{
//			return this.copy_kanri_cd;
//		}
//	   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
			
//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
		/**
		* コピー部門コードに対するセッター<br>
		* <br>
		* Ex)<br>
	    * setCopyBumonCd("文字列");<br>
		* <br>
		* @param String 設定する文字列
		*/
		   public boolean setCopyBumonCd(String copy_bumon_cd)
		   {
				this.copy_bumon_cd = copy_bumon_cd;
				return true;
			}
		/**
		* コピー部門コードに対するゲッター<br>
		* <br>
		* Ex)<br>
		* getCopyKanriCd();　戻り値　文字列<br>
		* <br>
		* @return String 文字列
		*/
		   public String getCopyBumonCd()
		   {
				return this.copy_bumon_cd;
			}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

/**
 * 仕入先コードに対するセッター<br>
 * <br>
 * Ex)<br>
 * setSiiresakiCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
/**
 * 仕入先コードに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSiiresakiCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSiiresakiCd()
	{
		return this.siiresaki_cd;
	}


/**
 * 正式名称(漢字)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanjiNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
/**
 * 正式名称(漢字)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanjiNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanjiNa()
	{
		return this.kanji_na;
	}


/**
 * 正式名称(カナ)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanaNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanaNa(String kana_na)
	{
		this.kana_na = kana_na;
		return true;
	}
/**
 * 正式名称(カナ)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanaNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanaNa()
	{
		return this.kana_na;
	}


/**
 * 略式名称(漢字)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanjiRn("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanjiRn(String kanji_rn)
	{
		this.kanji_rn = kanji_rn;
		return true;
	}
/**
 * 略式名称(漢字)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanjiRn();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanjiRn()
	{
		return this.kanji_rn;
	}


/**
 * 略式名称(カナ)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setKanaRn("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setKanaRn(String kana_rn)
	{
		this.kana_rn = kana_rn;
		return true;
	}
/**
 * 略式名称(カナ)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getKanaRn();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getKanaRn()
	{
		return this.kana_rn;
	}


/**
 * 郵便番号に対するセッター<br>
 * <br>
 * Ex)<br>
 * setYubinCd("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setYubinCd(String yubin_cd)
	{
		this.yubin_cd = yubin_cd;
		return true;
	}
/**
 * 郵便番号に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getYubinCd();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getYubinCd()
	{
		return this.yubin_cd;
	}


/**
 * 住所(漢字)1に対するセッター<br>
 * <br>
 * Ex)<br>
 * setAddressKanji1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setAddressKanji1Na(String address_kanji1_na)
	{
		this.address_kanji1_na = address_kanji1_na;
		return true;
	}
/**
 * 住所(漢字)1に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getAddressKanji1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getAddressKanji1Na()
	{
		return this.address_kanji1_na;
	}


/**
 * 住所(漢字)2に対するセッター<br>
 * <br>
 * Ex)<br>
 * setAddressKanji2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setAddressKanji2Na(String address_kanji2_na)
	{
		this.address_kanji2_na = address_kanji2_na;
		return true;
	}
/**
 * 住所(漢字)2に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getAddressKanji2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getAddressKanji2Na()
	{
		return this.address_kanji2_na;
	}


/**
 * 住所(カナ)1に対するセッター<br>
 * <br>
 * Ex)<br>
 * setAddressKana1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setAddressKana1Na(String address_kana1_na)
	{
		this.address_kana1_na = address_kana1_na;
		return true;
	}
/**
 * 住所(カナ)1に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getAddressKana1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getAddressKana1Na()
	{
		return this.address_kana1_na;
	}


/**
 * 住所(カナ)2に対するセッター<br>
 * <br>
 * Ex)<br>
 * setAddressKana2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setAddressKana2Na(String address_kana2_na)
	{
		this.address_kana2_na = address_kana2_na;
		return true;
	}
/**
 * 住所(カナ)2に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getAddressKana2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getAddressKana2Na()
	{
		return this.address_kana2_na;
	}


/**
 * 窓口担当者に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMadoguchiTantoNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMadoguchiTantoNa(String madoguchi_tanto_na)
	{
		this.madoguchi_tanto_na = madoguchi_tanto_na;
		return true;
	}
/**
 * 窓口担当者に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMadoguchiTantoNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMadoguchiTantoNa()
	{
		return this.madoguchi_tanto_na;
	}


/**
 * 通常連絡先TEL1に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTel1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTel1Na(String tel1_na)
	{
		this.tel1_na = tel1_na;
		return true;
	}
/**
 * 通常連絡先TEL1に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTel1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTel1Na()
	{
		return this.tel1_na;
	}


/**
 * 通常連絡先TEL2に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTel2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTel2Na(String tel2_na)
	{
		this.tel2_na = tel2_na;
		return true;
	}
/**
 * 通常連絡先TEL2に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTel2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTel2Na()
	{
		return this.tel2_na;
	}


/**
 * 通常連絡先FAX1に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFax1Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFax1Na(String fax1_na)
	{
		this.fax1_na = fax1_na;
		return true;
	}
/**
 * 通常連絡先FAX1に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFax1Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFax1Na()
	{
		return this.fax1_na;
	}


/**
 * 通常連絡先FAX2に対するセッター<br>
 * <br>
 * Ex)<br>
 * setFax2Na("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setFax2Na(String fax2_na)
	{
		this.fax2_na = fax2_na;
		return true;
	}
/**
 * 通常連絡先FAX2に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getFax2Na();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getFax2Na()
	{
		return this.fax2_na;
	}


/**
 * 夜間連絡先TEL(緊急)に対するセッター<br>
 * <br>
 * Ex)<br>
 * setNightTelNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setNightTelNa(String night_tel_na)
	{
		this.night_tel_na = night_tel_na;
		return true;
	}
/**
 * 夜間連絡先TEL(緊急)に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getNightTelNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getNightTelNa()
	{
		return this.night_tel_na;
	}


/**
 * E-MAILに対するセッター<br>
 * <br>
 * Ex)<br>
 * setEmailNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setEmailNa(String email_na)
	{
		this.email_na = email_na;
		return true;
	}
/**
 * E-MAILに対するゲッター<br>
 * <br>
 * Ex)<br>
 * getEmailNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getEmailNa()
	{
		return this.email_na;
	}


/**
 * 情報処理料金請求区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setJohosyoriSeikyuKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setJohosyoriSeikyuKb(String johosyori_seikyu_kb)
	{
		this.johosyori_seikyu_kb = johosyori_seikyu_kb;
		return true;
	}
/**
 * 情報処理料金請求区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getJohosyoriSeikyuKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getJohosyoriSeikyuKb()
	{
		return this.johosyori_seikyu_kb;
	}


/**
 * 商品コード印字区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setSyhincdPrintKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setSyhincdPrintKb(String syhincd_print_kb)
	{
		this.syhincd_print_kb = syhincd_print_kb;
		return true;
	}
/**
 * 商品コード印字区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getSyhincdPrintKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getSyhincdPrintKb()
	{
		return this.syhincd_print_kb;
	}


/**
 * タグ発行代行業者に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTaghakoGyosyaKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTaghakoGyosyaKb(String taghako_gyosya_kb)
	{
		this.taghako_gyosya_kb = taghako_gyosya_kb;
		return true;
	}
/**
 * タグ発行代行業者に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTaghakoGyosyaKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTaghakoGyosyaKb()
	{
		return this.taghako_gyosya_kb;
	}

//	↓↓削除（2005.06.30）↓↓
/**
 * 発行場所に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHakoBasyoNa("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
//	public boolean setHakoBasyoNa(String hako_basyo_na)
//	{
//		this.hako_basyo_na = hako_basyo_na;
//		return true;
//	}
/**
 * 発行場所に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHakoBasyoNa();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
//	public String getHakoBasyoNa()
//	{
//		return this.hako_basyo_na;
//	}
//	↑↑削除（2005.06.30）↑↑

/**
 * 品番別伝票区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setHinbanbetuDenpyoKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setHinbanbetuDenpyoKb(String hinbanbetu_denpyo_kb)
	{
		this.hinbanbetu_denpyo_kb = hinbanbetu_denpyo_kb;
		return true;
	}
/**
 * 品番別伝票区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getHinbanbetuDenpyoKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getHinbanbetuDenpyoKb()
	{
		return this.hinbanbetu_denpyo_kb;
	}


/**
 * メモ欄に対するセッター<br>
 * <br>
 * Ex)<br>
 * setMemoTx("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setMemoTx(String memo_tx)
	{
		this.memo_tx = memo_tx;
		return true;
	}
/**
 * メモ欄に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getMemoTx();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getMemoTx()
	{
		return this.memo_tx;
	}


/**
 * 倒産区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setTosanKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setTosanKb(String tosan_kb)
	{
		this.tosan_kb = tosan_kb;
		return true;
	}
/**
 * 倒産区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getTosanKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getTosanKb()
	{
		return this.tosan_kb;
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
	

//↓↓仕様追加による変更（2005.05.24）
/**
 * EOS区分に対するセッター<br>
 * <br>
 * Ex)<br>
 * setEosKb("文字列");<br>
 * <br>
 * @param String 設定する文字列
 */
	public boolean setEosKb(String eos_kb)
	{
		this.eos_kb = eos_kb;
		return true;
	}
/**
 * EOS区分に対するゲッター<br>
 * <br>
 * Ex)<br>
 * getEosKb();　戻り値　文字列<br>
 * <br>
 * @return String 文字列
 */
	public String getEosKb()
	{
		return this.eos_kb;
	}
//	↑↑仕様追加による変更（2005.05.24）

//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	↓↓課金種別コード　追加 JINNO (2006.03.29) ↓↓		
//	/**
//	 * 課金種別コードに対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setEosKb("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setKakinSyubetuCd(String kakin_syubetu_cd)
//		{
//			this.kakin_syubetu_cd = kakin_syubetu_cd;
//			return true;
//		}
//	/**
//	 * 課金種別コードに対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getEosKb();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getKakinSyubetuCd()
//		{
//			return this.kakin_syubetu_cd;
//		}
//	↑↑課金種別コード　追加 JINNO (2006.03.29) ↑↑
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//	   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓	
	/**
	 * アスティ区分に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setAsutexiKb("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setAsutexiKb(String asty_kb)
		{
			this.asty_kb = asty_kb;
			return true;
		}
	/**
	 * アスティ区分に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getAsutexiKb();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getAsutexiKb()
		{
			return this.asty_kb;
		}
//		   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
	
//		   ↓↓2006.06.22 wangzhg カスタマイズ修正↓↓	
		/**
		 * 販売データ契約区分に対するセッター<br>
		 * <br>
		 * Ex)<br>
		 * setHanbaiKeiyakuKb("文字列");<br>
		 * <br>
		 * @param String 設定する文字列
		 */
			public boolean setHanbaiKeiyakuKb(String hanbai_keiyaku_kb)
			{
				this.hanbai_keiyaku_kb = hanbai_keiyaku_kb;
				return true;
			}
		/**
		 * 販売データ契約区分に対するゲッター<br>
		 * <br>
		 * Ex)<br>
		 * getHanbaiKeiyakuKb();　戻り値　文字列<br>
		 * <br>
		 * @return String 文字列
		 */
			public String getHanbaiKeiyakuKb()
			{
				return this.hanbai_keiyaku_kb;
			}
//			   ↑↑2006.06.22 wangzhg カスタマイズ修正↑↑
		
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

//↓↓2006.06.22 wangzhg カスタマイズ修正↓↓
//	/**
//	 * 管理コード名に対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setKanriNm("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setKanriNm(String kanri_nm)
//		{
//			this.kanri_nm = kanri_nm;
//			return true;
//		}
//	/**
//	 * 管理コード名に対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getKanriNm();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getKanriNm()
//		{
//			return this.kanri_nm;
//		}
//↑↑2006.06.22 wangzhg カスタマイズ修正↑↑

//	/**
//	 * コピー管理コード名に対するセッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * setKanriNm("文字列");<br>
//	 * <br>
//	 * @param String 設定する文字列
//	 */
//		public boolean setCopyKanriNm(String copykanri_nm)
//		{
//			this.copykanri_nm = copykanri_nm;
//			return true;
//		}
//	/**
//	 * コピー管理コード名に対するゲッター<br>
//	 * <br>
//	 * Ex)<br>
//	 * getKanriNm();　戻り値　文字列<br>
//	 * <br>
//	 * @return String 文字列
//	 */
//		public String getCopyKanriNm()
//		{
//			return this.copykanri_nm;
//		}

			
		/**
		* コピー部門コード名に対するセッター<br>
		* <br>
		* Ex)<br>
		* setBumonNm("文字列");<br>
		* <br>
		* @param String 設定する文字列
		*/
		public boolean setCopyBumonNm(String copybumon_nm)
		{
			this.copybumon_nm = copybumon_nm;
			return true;
		}
	    /**
		* コピー部門コード名に対するゲッター<br>
		* <br>
		* Ex)<br>
		* getBumonNm();　戻り値　文字列<br>
		* <br>
		* @return String 文字列
		*/
		public String getCopyBumonNm()
		{
			return this.copybumon_nm;
		}

	/**
	 * 仕入先名に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanriNm("文字列");<br>
	 * <br>
	 * @param setSiiresakiNm 設定する文字列
	 */
		public boolean setSiiresakiNm(String siiresaki_nm)
		{
			this.siiresaki_nm = siiresaki_nm;
			return true;
		}
	/**
	 * 仕入先名に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getSiiresakiNm();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getSiiresakiNm()
		{
			return this.siiresaki_nm;
		}

	/**
	 * 生鮮担当者判断フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getCopyKanriKanjiRn();　戻り値　生鮮担当者判断フラグ<br>
	 * <br>
	 * @return boolean 生鮮担当者判断フラグ
	 */
	public boolean isSeisen() {
		return seisen;
	}

	/**
	 * 生鮮担当者判断フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setSeisen(boolean);<br>
	 * <br>
	 * @param String 設定する生鮮担当者判断フラグ
	 */
	public void setSeisen(boolean b) {
		seisen = b;
	}

//	BUGNO-S005 2005.04.22 T.Makuta START
	/**
	 * 変更フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setChangeflg("文字列");<br>
	 * <br>
	 * @param setSiiresakiNm 設定する文字列
	 */
		public boolean setChangeflg(String changeflg)
		{
			this.changeflg = changeflg;
			return true;
		}
	/**
	 * 変更フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getChangeflg();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
		public String getChangeflg()
		{
			return this.changeflg;
		}
//	BUGNO-S005 2005.04.22 T.Makuta END

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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
		if(this.errorFlg.equals(mst000101_ConstDictionary.ERR_CHK_NORMAL)
		|| this.errorFlg.equals("")){
			//通常系
			if(!this.errorMessage.equals("")){
				stcLog.getLog().info(logHeader + this.errorMessage);
			}
		} else {
			//エラー系
			stcLog.getLog().error(logHeader + this.errorMessage);
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
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}
	
	
//	 ADD by Tanigawa 2006/9/25 障害票№0036対応 POS、タグの画面状態保持用変数宣言 START
	/**
	 * bPos を戻します。
	 * @return bPos
	 */
	public boolean isPosChecked() {
		return this.bPos;
	}
	/**
	 * pos を設定
	 * @param pos
	 */
	public void setBPos(boolean pos) {
		this.bPos = pos;
	}

	/**
	 * bTag を戻します。
	 * @return bTag
	 */
	public boolean isTagChecked() {
		return this.bTag;
	}
	/**
	 * tag を設定
	 * @param tag
	 */
	public void setBTag(boolean tag) {
		this.bTag = tag;
	}
//	 ADD by Tanigawa 2006/9/25 障害票№0036対応 POS、タグの画面状態保持用変数宣言  END 

//	 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加 START
	/**
	 * copy_chiku_cd を戻します。
	 * @return copy_chiku_cd
	 */
	public String getCopyChikuCd() {
		return (this.copy_chiku_cd == null) ? "" : this.copy_chiku_cd.trim();
	}

	/**
	 * copy_chiku_cd を設定
	 * @param copy_chiku_cd
	 */
	public void setCopyChikuCd(String copy_chiku_cd) {
		this.copy_chiku_cd = copy_chiku_cd;
	}
//	 ADD by Tanigawa 2006/9/27 障害票№0057対応 コピー地区コード追加  END 

}
