/**
 * <p>タイトル: 登録票BY承認の画面名細部表示データ格納用クラス</p>
 * <p>説明: 登録票BY承認の画面名細部表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.stc.log.StcLog;

/** 
 * <p>タイトル: 登録票BY承認の画面名細部表示データ格納用クラス</p>
 * <p>説明: 登録票BY承認の画面名細部表示データ格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author hujh
 * @version 1.0(2006/06/27)初版作成
 */
public class mstA70301_KeepMeisaiBean
{

	private List Meisai		= new ArrayList();		// 一覧の明細
	private String DispRows		= "30";				// 一画面表示明細数
	private String CurrentPageNo	= "";				// 現在表示ページ
	private String LastPageNo		= "";				// 最終ページ
	private String MaxRows			= "0";			// 最大行数
	private String EndRowInPage	= "";				// 現在ページの終了行
	private String StartRowInPage	= "";				// 現在ページの開始行
	private String PageMode		= "";				// ページ遷移の処理状況
	private String Sentaku			= null;			// 選択
	private String UketsukeNo		= null;			// 受付番号
	private String Keika				= null;			// 経過
	private String Syonincommenttx		= null;	// BYコメント
	private String SiireskiKanjiNa	= null;			// 仕入先
	private String SiireskiKanjiCd	= null;			// 仕入先コード
	private String Uploadcommenttx		= null;	// 仕入先コメント
	private String ByNo		= null;					// BY　No
	private String ExcelReigaiKb	= null;			// 例外区分
	private String ExcelSyohinKb	= null;			// 商品区分
	private String ExcelTanpinKb	= null;			// 単品区分
	private String ExcelSyokaiKb	= null;			// 初回区分
	private String SyuseiKb			= null;			// 修正区分
	private String Kensu				= null;			// 件数
	// ===BEGIN=== Add by kou 2006/8/16
	private String TanpinSyuseiKb	= null;			// 店指示修正区分
	private String TanpinKensu		= null;			// 店指示件数
	private String ReigaiSyuseiKb	= null;			// 店別例外修正区分
	private String ReigaiKensu		= null;			// 店別例外件数
	private String SyokaiSyuseiKb	= null;			// 本部投入修正区分
	private String SyokaiKensu		= null;			// 本部投入件数
	// ===END=== Add by kou 2006/8/16
	private String MinNeireRt		= null;			// 最低値入率
	private String MaxNeireRt		= null;			// 最高値入率
	private String MaxUritankaVl	= null;			// 最大売単価
	private String TorikomiDt		= null;			// アップロード日時
	private String TorokuSyoninTs= null;			// 承認日時
	private String TorokuSyoninFg= null;			// 登録承認フラグ
//	↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
	private String InsertTs		    = null;			// 登録年月日
//	↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
	private String UpdateTs		    = null;			// 更新年月日
	private String UpdateUserId	= null;			// 更新者ID
	private String ExcelFileSyubetu	= null;		// EXCELファイル種別
	private String TblNm				= null;			
	private String selectFlg			= null;	
	private String radioflag			= null;	
	private String torokuSyoninFgShoki	= null;	
	private static final StcLog stcLog = StcLog.getInstance();
//	↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
	private String SyoriJyotaiFg= null;				// 処理状態フラグ
//	↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑
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
	
	// Radioflagに対するセッターとゲッターの集合
	public String getRadioflag() {
		return radioflag;
	}
	
	public void setRadioflag(String radioflag) {
		this.radioflag = radioflag;
	}
	
	// TorokuSyoninFgShokiに対するセッターとゲッターの集合
	public String getTorokuSyoninFgShoki() {
		return torokuSyoninFgShoki;
	}
	
	public void setTorokuSyoninFgShoki(String torokuSyoninFgShoki) {
		this.torokuSyoninFgShoki = torokuSyoninFgShoki;
	}
	
	
	// SiireskiKanjiCdに対するセッターとゲッターの集合
	public String getSiireskiKanjiCd() {
		return SiireskiKanjiCd;
	}
	
	public void setSiireskiKanjiCd(String siireskiKanjiCd) {
		SiireskiKanjiCd = siireskiKanjiCd;
	}
	
	// ExcelFileSyubetuに対するセッターとゲッターの集合
	public String getExcelFileSyubetu() {
		return ExcelFileSyubetu;
	}
	
	public void setExcelFileSyubetu(String excelFileSyubetu) {
		ExcelFileSyubetu = excelFileSyubetu;
	}
	
	// TblNmに対するセッターとゲッターの集合
	public String getTblNm() {
		return TblNm;
	}
	
	public void setTblNm(String tblNm) {
		TblNm = tblNm;
	}
	
	
	public String getSelectFlg() {
		return selectFlg;
	}
	// selectFlgに対するセッターとゲッターの集合
	public void setSelectFlg(String selectFlg) {
		this.selectFlg = selectFlg;
	}
	
	// TorokuSyoninFgに対するセッターとゲッターの集合
	public String getTorokuSyoninFg() {
		return TorokuSyoninFg;
	}
	
	public void setTorokuSyoninFg(String torokuSyoninFg) {
		TorokuSyoninFg = torokuSyoninFg;
	}
	
//	↓↓2006.10.06 H.Yamamoto カスタマイズ修正↓↓
	// SyoriJyotaiFgに対するセッターとゲッターの集合
	public String getSyoriJyotaiFg() {
		return SyoriJyotaiFg;
	}
	
	public void setSyoriJyotaiFg(String syoriJyotaiFg) {
		SyoriJyotaiFg = syoriJyotaiFg;
	}
//	↑↑2006.10.06 H.Yamamoto カスタマイズ修正↑↑

//	↓↓2006.07.29 H.Yamamoto カスタマイズ修正↓↓
	// InsertTsに対するセッターとゲッターの集合
	public String getInsertTs() {
		return InsertTs;
	}
	
	public void setInsertTs(String insertTs) {
		InsertTs = insertTs;
	}
//	↑↑2006.07.29 H.Yamamoto カスタマイズ修正↑↑
	
	// UpdateTsに対するセッターとゲッターの集合
	public String getUpdateTs() {
		return UpdateTs;
	}
	
	public void setUpdateTs(String updateTs) {
		UpdateTs = updateTs;
	}
	
	// UpdateUserIdに対するセッターとゲッターの集合
	public String getUpdateUserId() {
		return UpdateUserId;
	}
	
	public void setUpdateUserId(String updateUserId) {
		UpdateUserId = updateUserId;
	}
	
	// ByNoに対するセッターとゲッターの集合
	public String getByNo() {
		return ByNo;
	}

	public boolean setByNo(String byNo) {
		ByNo = byNo;
		return true;
	}

	// ExcelReigaiKbに対するセッターとゲッターの集合
	public String getExcelReigaiKb() {
		return ExcelReigaiKb;
	}

	public boolean setExcelReigaiKb(String excelReigaiKb) {
		ExcelReigaiKb = excelReigaiKb;
		return true;
	}

	// ExcelSyohinKbに対するセッターとゲッターの集合
	public String getExcelSyohinKb() {
		return ExcelSyohinKb;
	}

	public boolean setExcelSyohinKb(String excelSyohinKb) {
		ExcelSyohinKb = excelSyohinKb;
		return true;
	}

	// ExcelSyokaiKbに対するセッターとゲッターの集合
	public String getExcelSyokaiKb() {
		return ExcelSyokaiKb;
	}

	public boolean setExcelSyokaiKb(String excelSyokaiKb) {
		ExcelSyokaiKb = excelSyokaiKb;
		return true;
	}

	// ExcelTanpinKbに対するセッターとゲッターの集合
	public String getExcelTanpinKb() {
		return ExcelTanpinKb;
	}

	public boolean setExcelTanpinKb(String excelTanpinKb) {
		ExcelTanpinKb = excelTanpinKb;
		return true;
	}

	// Keikaに対するセッターとゲッターの集合
	public String getKeika() {
		return Keika;
	}

	public boolean setKeika(String keika) {
		Keika = keika;
		return true;
	}

	// Kensuに対するセッターとゲッターの集合
	public String getKensu() {
		return Kensu;
	}

	public boolean setKensu(String kensu) {
		Kensu = kensu;
		return true;
	}

	// MaxNeireRtに対するセッターとゲッターの集合
	public String getMaxNeireRt() {
		return MaxNeireRt;
	}

	public boolean setMaxNeireRt(String maxNeireRt) {
		MaxNeireRt = maxNeireRt;
		return true;
	}

	// MaxUritankaVlに対するセッターとゲッターの集合
	public String getMaxUritankaVl() {
		return MaxUritankaVl;
	}

	public boolean setMaxUritankaVl(String maxUritankaVl) {
		MaxUritankaVl = maxUritankaVl;
		return true;
	}

	// MinNeireRtに対するセッターとゲッターの集合
	public String getMinNeireRt() {
		return MinNeireRt;
	}

	public boolean setMinNeireRt(String minNeireRt) {
		MinNeireRt = minNeireRt;
		return true;
	}

	// Sentakuに対するセッターとゲッターの集合
	public String getSentaku() {
		return Sentaku;
	}

	public boolean setSentaku(String sentaku) {
		Sentaku = sentaku;
		return true;
	}

	// SiireskiKanjiNaに対するセッターとゲッターの集合
	public String getSiireskiKanjiNa() {
		return SiireskiKanjiNa;
	}

	public boolean setSiireskiKanjiNa(String siireskiKanjiNa) {
		SiireskiKanjiNa = siireskiKanjiNa;
		return true;
	}

	// Syonincommenttxに対するセッターとゲッターの集合
	public String getSyonincommenttx() {
		return cutString(Syonincommenttx,40);
	}

	public boolean setSyonincommenttx(String syonincommenttx) {
		Syonincommenttx = syonincommenttx;
		return true;
	}

	// SyuseiKbに対するセッターとゲッターの集合
	public String getSyuseiKb() {
		return SyuseiKb;
	}

	public boolean setSyuseiKb(String syuseiKb) {
		SyuseiKb = syuseiKb;
		return true;
	}

	// TorikomiDtに対するセッターとゲッターの集合
	public String getTorikomiDt() {
		return TorikomiDt;
	}

	public boolean setTorikomiDt(String torikomiDt) {
		TorikomiDt = torikomiDt;
		return true;
	}

	// TorokuSyoninTsに対するセッターとゲッターの集合
	public String getTorokuSyoninTs() {
		return TorokuSyoninTs;
	}

	public boolean setTorokuSyoninTs(String torokuSyoninTs) {
		TorokuSyoninTs = torokuSyoninTs;
		return true;
	}

	// UketsukeNoに対するセッターとゲッターの集合
	public String getUketsukeNo() {
		return UketsukeNo;
	}

	public boolean setUketsukeNo(String uketsukeNo) {
		UketsukeNo = uketsukeNo;
		return true;
	}

	// Uploadcommenttxに対するセッターとゲッターの集合
	public String getUploadcommenttx() {
		return Uploadcommenttx;
	}

	public boolean setUploadcommenttx(String uploadcommenttx) {
		Uploadcommenttx = uploadcommenttx;
		return true;
	}

	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(List Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public List getMeisai() {
		return this.Meisai;
	}

	// DispRowsに対するセッターとゲッターの集合
	public boolean setDispRows(String DispRows) {
		this.DispRows = DispRows;
		return true;
	}
	
	public String getDispRows() {
		return this.DispRows;
	}
	
	// CurrentPageNoに対するセッターとゲッターの集合
	public boolean setCurrentPageNo(String CurrentPageNo) {
		this.CurrentPageNo = CurrentPageNo;
		return true;
	}
	
	public String getCurrentPageNo() {
		return this.CurrentPageNo;
	}
	
	// LastPageNoに対するセッターとゲッターの集合
	public boolean setLastPageNo(String LastPageNo) {
		this.LastPageNo = LastPageNo;
		return true;
	}
	
	public String getLastPageNo() {
		return this.LastPageNo;
	}
	
	// MaxRowsに対するセッターとゲッターの集合
	public boolean setMaxRows(String MaxRows) {
		this.MaxRows = MaxRows;
		return true;
	}
	
	public String getMaxRows() {
		return this.MaxRows;
	}
	
	// EndRowInPageに対するセッターとゲッターの集合
	public boolean setEndRowInPage(String EndRowInPage) {
		this.EndRowInPage = EndRowInPage;
		return true;
	}
	
	public String getEndRowInPage() {
		return this.EndRowInPage;
	}

	// StartRowInPageに対するセッターとゲッターの集合
	public boolean setStartRowInPage(String StartRowInPage) {
		this.StartRowInPage = StartRowInPage;
		return true;
	}
	
	public String getStartRowInPage() {
		return this.StartRowInPage;
	}
	
	// PageModeに対するセッターとゲッターの集合
	public boolean setPageMode(String PageMode) {
		this.PageMode = PageMode;
		return true;
	}
	
	public String getPageMode() {
		return this.PageMode;
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
	
	// ===BEGIN=== Add by kou 2006/8/16
	/**
	 * @return
	 */
	public String getReigaiKensu() {
		return ReigaiKensu;
	}

	/**
	 * @return
	 */
	public String getReigaiSyuseiKb() {
		return ReigaiSyuseiKb;
	}

	/**
	 * @return
	 */
	public String getSyokaiKensu() {
		return SyokaiKensu;
	}

	/**
	 * @return
	 */
	public String getSyokaiSyuseiKb() {
		return SyokaiSyuseiKb;
	}

	/**
	 * @return
	 */
	public String getTanpinKensu() {
		return TanpinKensu;
	}

	/**
	 * @return
	 */
	public String getTanpinSyuseiKb() {
		return TanpinSyuseiKb;
	}

	/**
	 * @param string
	 */
	public void setReigaiKensu(String string) {
		ReigaiKensu = string;
	}

	/**
	 * @param string
	 */
	public void setReigaiSyuseiKb(String string) {
		ReigaiSyuseiKb = string;
	}

	/**
	 * @param string
	 */
	public void setSyokaiKensu(String string) {
		SyokaiKensu = string;
	}

	/**
	 * @param string
	 */
	public void setSyokaiSyuseiKb(String string) {
		SyokaiSyuseiKb = string;
	}

	/**
	 * @param string
	 */
	public void setTanpinKensu(String string) {
		TanpinKensu = string;
	}

	/**
	 * @param string
	 */
	public void setTanpinSyuseiKb(String string) {
		TanpinSyuseiKb = string;
	}
	// ===END=== Add by kou 2006/8/16

}