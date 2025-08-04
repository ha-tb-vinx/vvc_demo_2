/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.master.common.dictionary.mst005701_AsortPatternDictionary;
import mdware.master.common.dictionary.mst000101_ConstDictionary;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）初回導入マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する初回導入マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Koyama
 * @version 1.0(2005/03/24)初版作成
 */
public class mst260101_SyokaiDonyuIryouBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int HANKU_CD_MAX_LENGTH = 4;//販区コードの長さ
	
//****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;//仕入先コードの長さ

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
//	public static final int HACHUNO_CD_MAX_LENGTH = 6;//発注コードの長さ
	public static final int HACHUNO_CD_MAX_LENGTH = 10;//発注コードの長さ
	
	public static final int SYOHIN_CD_MAX_LENGTH = 13;//商品コードの長さ
	public static final int THEME_CD_MAX_LENGTH = 2;//テーマコードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int TENPO_NM_MAX_LENGTH = 20;//店舗コード名称の長さ
	public static final int COLOR_CD_MAX_LENGTH = 2;//カラーコードの長さ
	public static final int SIZE_CD_MAX_LENGTH = 2;//サイズコードの長さ
	public static final int ASORT_PATTERN_CD_MAX_LENGTH = 2;//アソートパターンの長さ
	public static final int HATYU_DT_MAX_LENGTH = 8;//発注日の長さ
	public static final int NOHIN_DT_MAX_LENGTH = 8;//納品日の長さ
	public static final int INSERT_TS_MAX_LENGTH = 20;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;//更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;//削除フラグの長さ
	
//	↓↓仕様変更（2005.07.07）↓↓
	public static final int SENTAKU_MAX_LENGTH = 1;			//処理選択
//	↑↑仕様変更（2005.07.07）↑↑

	private String hanku_cd = null;	//販区コード
	
//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	private String siiresaki_cd = null;	//仕入先コード

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	private String hachuno_cd = null;	//発注コード
	
	private String syohin_cd = null;	//商品コード
	private String theme_cd = null;	//テーマコード
	private String tenpo_cd = null;	//店舗コード
	private String tenpo_nm = null;	//店舗コード名称
	private String suryo_qt = null;	//数量
	private String suryo_qt_old = null;	//数量(検索時の値)
	private String gentanka_vl = null;	//原価単価
	private String baitanka_vl = null;	//売価単価(税込)
	private String color_cd = null;	//カラーコード
	private String size_cd = null;	//サイズコード
	private String asort_pattern_cd = null;	//アソートパターン
	private String hatyu_dt = null;	//発注日
	private String nohin_dt = null;	//納品日
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String delete_fg = null;	//削除フラグ
	
//	↓↓仕様変更（2005.07.07）↓↓
	private String sentaku = null;			//処理選択
//	↑↑仕様変更（2005.07.07）↑↑

//	↓↓仕様変更（2005.10.14）↓↓
	private String hachutani_qt = null;	//発注単位
//	↑↑仕様変更（2005.10.14）↑↑

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
	 * mst260101_SyokaiDonyuIryouBeanを１件のみ抽出したい時に使用する
	 */
	public static mst260101_SyokaiDonyuIryouBean getmst260101_SyokaiDonyuIryouBean(DataHolder dataHolder)
	{
		mst260101_SyokaiDonyuIryouBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst260101_SyokaiDonyuIryouDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst260101_SyokaiDonyuIryouBean )ite.next();
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

//	****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//	// siiresaki_cdに対するセッターとゲッターの集合
//	public boolean setSiiresakiCd(String siiresaki_cd)
//	{
//		this.siiresaki_cd = siiresaki_cd;
//		return true;
//	}
//	public String getSiiresakiCd()
//	{
//		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
//	}
//	public String getSiiresakiCdString()
//	{
//		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
//	}
//	public String getSiiresakiCdHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
//	}

//	****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
	// hachuno_cdに対するセッターとゲッターの集合
	public boolean setHachunoCd(String hachuno_cd)
	{
		this.hachuno_cd = hachuno_cd;
		return true;
	}
	public String getHachunoCd()
	{
		return cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH);
	}
	public String getHachunoCdString()
	{
		return "'" + cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH) + "'";
	}
	public String getHachunoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hachuno_cd,HACHUNO_CD_MAX_LENGTH));
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

	// tenpo_nmに対するセッターとゲッターの集合
	public boolean setTenpoNm(String tenpo_nm)
	{
		this.tenpo_nm = tenpo_nm;
		return true;
	}
	public String getTenpoNm()
	{
		return cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH);
	}
	public String getTenpoNmString()
	{
		return "'" + cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH) + "'";
	}
	public String getTenpoNmHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_nm,TENPO_NM_MAX_LENGTH));
	}

	// suryo_qtに対するセッターとゲッターの集合
	public boolean setSuryoQt(String suryo_qt)
	{
		this.suryo_qt = suryo_qt;
		return true;
	}
	public String getSuryoQt()
	{
		return this.suryo_qt;
	}
	public String getSuryoQtHTMLString()
	{
		String suryo = this.suryo_qt;
		if(suryo == null || suryo.equals("")){
			return "";
		}
	
		suryo = mst000401_LogicBean.addComma(suryo);

		return HTMLStringUtil.convert(suryo);
	}

	// suryo_qt_oldに対するセッターとゲッターの集合
	public boolean setSuryoQtOld(String suryo_qt_old)
	{
		this.suryo_qt_old = suryo_qt_old;
		return true;
	}
	public String getSuryoQtOld()
	{
		return this.suryo_qt_old;
	}
	public String getSuryoQtOldHTMLString()
	{
		String suryo_old = this.suryo_qt_old;
		if(suryo_old == null || suryo_old.equals("")){
			return "";
		}
	
		suryo_old = mst000401_LogicBean.addComma(suryo_old);

		return HTMLStringUtil.convert(suryo_old);
	}

	// gentanka_vlに対するセッターとゲッターの集合
	public boolean setGentankaVl(String gentanka_vl)
	{
		//Stringでデータを受け取っているので
		//少数点が先頭の場合、0を付加する
		String val = gentanka_vl;
		
		if(val != null && !val.equals("")){
			if(val.substring(0,1).equals(".")){
				val = "0" + val;
			}
			
			if(val.indexOf(".") == -1){
				val = val + ".00";
			}
			
		}

		this.gentanka_vl = val;
		
		return true;
	}
	public String getGentankaVl()
	{
		return this.gentanka_vl;
	}
	public String getGentankaVlHTMLString()
	{
		String genka = this.gentanka_vl;
		if(genka == null || genka.equals("")){
			return "";
		}
	
		genka = mst000401_LogicBean.addCommaDecimal(genka,mst000101_ConstDictionary.FUNCTION_PARAM_0,"2");

		return HTMLStringUtil.convert(genka);
	}


	// baitanka_vlに対するセッターとゲッターの集合
	public boolean setBaitankaVl(String baitanka_vl)
	{
		this.baitanka_vl = baitanka_vl;
		return true;
	}
	public String getBaitankaVl()
	{
		return this.baitanka_vl;
	}
	public String getBaitankaVlHTMLString()
	{
		String baitank = this.baitanka_vl;
		if(baitank == null || baitank.equals("")){
			return "";
		}
	
		baitank = mst000401_LogicBean.addComma(baitank);

		return HTMLStringUtil.convert(baitank);
	}


	// color_cdに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd)
	{
		this.color_cd = color_cd;
		return true;
	}
	public String getColorCd()
	{
		return cutString(this.color_cd,COLOR_CD_MAX_LENGTH);
	}
	public String getColorCdString()
	{
		return "'" + cutString(this.color_cd,COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getColorCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.color_cd,COLOR_CD_MAX_LENGTH));
	}


	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd)
	{
		this.size_cd = size_cd;
		return true;
	}
	public String getSizeCd()
	{
		return cutString(this.size_cd,SIZE_CD_MAX_LENGTH);
	}
	public String getSizeCdString()
	{
		return "'" + cutString(this.size_cd,SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getSizeCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.size_cd,SIZE_CD_MAX_LENGTH));
	}

	//アソートパターンのOption作成
	public String getColorSizeHTMLOptionString(List asortList)
	{
		
		if(color_cd == null){
			color_cd = "";
		}
		color_cd = color_cd.trim();
		
		if(size_cd == null){
			size_cd = "";
		}
		size_cd = size_cd.trim();
		
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("<OPTION VALUE=\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</OPTION>\n");


		for (int i = 0; i < asortList.size(); i++) {
			mst260401_AsortmentBean bean = (mst260401_AsortmentBean)asortList.get(i);

			//アソートパターン2
			buf.append("<OPTION VALUE=\"");
			buf.append(bean.getColorCd().trim() + ":" + bean.getSizeCd().trim());
			buf.append("\"");
			if(bean.getColorCd().trim().equals(color_cd) && bean.getSizeCd().trim().equals(size_cd)){
				buf.append(" SELECTED ");
			}
			buf.append(" >");
			buf.append(bean.getColorCd().trim() + ":" + bean.getColorNm().trim());
			buf.append(bean.getSizeCd().trim() + ":" + bean.getSizeNm().trim());
			buf.append("</OPTION>\n");
			
		}

		return buf.toString();
	}

	//アソートパターンの名称取得
	public String getColorSizeNameHTMLString(List asortList)
	{
		
		if(color_cd == null){
			color_cd = "";
		}
		color_cd = color_cd.trim();
		
		if(size_cd == null){
			size_cd = "";
		}
		size_cd = size_cd.trim();
		
		
		for (int i = 0; i < asortList.size(); i++) {
			mst260401_AsortmentBean bean = (mst260401_AsortmentBean)asortList.get(i);

			if(bean.getColorCd().trim().equals(color_cd) && bean.getSizeCd().trim().equals(size_cd)){
				return bean.getColorCd().trim() + ":" + bean.getColorNm().trim() +
						bean.getSizeCd().trim() + ":" + bean.getSizeNm().trim();
			}
		}

		return "";
	}



	// asort_pattern_cdに対するセッターとゲッターの集合
	public boolean setAsortPatternCd(String asort_pattern_cd)
	{
		this.asort_pattern_cd = asort_pattern_cd;
		return true;
	}
	public String getAsortPatternCd()
	{
		return cutString(this.asort_pattern_cd,ASORT_PATTERN_CD_MAX_LENGTH);
	}
	public String getAsortPatternCdString()
	{
		return "'" + cutString(this.asort_pattern_cd,ASORT_PATTERN_CD_MAX_LENGTH) + "'";
	}
	public String getAsortPatternCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.asort_pattern_cd,ASORT_PATTERN_CD_MAX_LENGTH));
	}
	//アソートパターンのOption作成
	public String getAsortPatternHTMLOptionString()
	{
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("<OPTION VALUE=\"\"></OPTION>\n");
		buf.append(getOption(mst005701_AsortPatternDictionary.PATTERN1));
		buf.append(getOption(mst005701_AsortPatternDictionary.PATTERN2));
		buf.append(getOption(mst005701_AsortPatternDictionary.PATTERN3));
		buf.append(getOption(mst005701_AsortPatternDictionary.PATTERN4));

		return buf.toString();
	}
	private String getOption(mst005701_AsortPatternDictionary dictionary){

		StringBuffer buf = new StringBuffer();

		if(asort_pattern_cd == null){
			asort_pattern_cd = "";
		}
		asort_pattern_cd = asort_pattern_cd.trim();

		//アソートパターン2
		buf.append("<OPTION VALUE=\"");
		buf.append(dictionary.getCode());
		buf.append("\"");
		if(dictionary.getCode().equals(asort_pattern_cd)){
			buf.append(" SELECTED ");
		}
		buf.append(" >");
		buf.append(dictionary.toString());
		buf.append("</OPTION>\n");

		
		return buf.toString();		
	}
	//アソートパターン名称取得
	public String getAsortPatternNameHTMLString()
	{
		
		if(asort_pattern_cd == null){
			asort_pattern_cd = "";
		}
		asort_pattern_cd = asort_pattern_cd.trim();
		
		String name = mst005701_AsortPatternDictionary.getStatus(asort_pattern_cd).toString();
		
		if(name.equals(mst005701_AsortPatternDictionary.UNKNOWN.toString())){
			name = "";
		}

		return name;
	}


	// hatyu_dtに対するセッターとゲッターの集合
	public boolean setHatyuDt(String hatyu_dt)
	{
		this.hatyu_dt = hatyu_dt;
		return true;
	}
	public String getHatyuDt()
	{
		return cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH);
	}
	public String getHatyuDtString()
	{
		return "'" + cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH) + "'";
	}
	public String getHatyuDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hatyu_dt,HATYU_DT_MAX_LENGTH));
	}


	// nohin_dtに対するセッターとゲッターの集合
	public boolean setNohinDt(String nohin_dt)
	{
		this.nohin_dt = nohin_dt;
		return true;
	}
	public String getNohinDt()
	{
		return cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH);
	}
	public String getNohinDtString()
	{
		return "'" + cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH) + "'";
	}
	public String getNohinDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.nohin_dt,NOHIN_DT_MAX_LENGTH));
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
	
	
//	↓↓仕様変更（2005.07.07）↓↓
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
//	↑↑仕様変更（2005.07.07）↑↑

	// hachutani_qtに対するセッターとゲッターの集合
	public boolean setHachutaniQt(String hachutani_qt)
	{
		//Stringでデータを受け取っているので
		//少数点が先頭の場合、0を付加する
		String val = hachutani_qt;
		
		if(val != null && !val.equals("")){
			if(val.substring(0,1).equals(".")){
				val = "0" + val;
			}
			
			if(val.indexOf(".") == -1){
				val = val + ".0";
			}
			
		}

		this.hachutani_qt = val;
		
		return true;
	}
	public String getHachutaniQt()
	{
		return this.hachutani_qt;
	}
	public String getHachutaniQtHTMLString()
	{
		String hachutani = this.hachutani_qt;
		if(hachutani == null || hachutani.equals("")){
			return "";
		}
	
		hachutani = mst000401_LogicBean.addCommaDecimal(hachutani,mst000101_ConstDictionary.FUNCTION_PARAM_0,"1");

		return HTMLStringUtil.convert(hachutani);
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		+ "  siiresaki_cd = " + getSiiresakiCdString()
//		****** DB Var3.6 修正箇所 *****発注NO.の追加**********************

		return "  hanku_cd = " + getHankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  hachuno_cd = " + getHachunoCdString()
			+ "  theme_cd = " + getThemeCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpo_nm = " + getTenpoNmString()
			+ "  suryo_qt = " + getSuryoQt()
			+ "  suryo_qt_old = " + getSuryoQtOld()
			+ "  gentanka_vl = " + getGentankaVl()
			+ "  baitanka_vl = " + getBaitankaVl()
			+ "  color_cd = " + getColorCdString()
			+ "  size_cd = " + getSizeCdString()
			+ "  asort_pattern_cd = " + getAsortPatternCdString()
			+ "  hatyu_dt = " + getHatyuDtString()
			+ "  nohin_dt = " + getNohinDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
//		↓↓仕様変更（2005.07.07）↓↓
			+ "  sentaku = " + getSentaku()
//		↑↑仕様変更（2005.07.07）↑↑
//		↓↓仕様変更（2005.10.14）↓↓
			+ "  hachutani_qt = " + getHachutaniQt()
//		↑↑仕様変更（2005.10.14）↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst260101_SyokaiDonyuIryouBean コピー後のクラス
	 */
	public mst260101_SyokaiDonyuIryouBean createClone()
	{
		mst260101_SyokaiDonyuIryouBean bean = new mst260101_SyokaiDonyuIryouBean();
		bean.setHankuCd(this.hanku_cd);
//		****** DB Var3.6 修正箇所 *****仕入先コード削除**********************
//		bean.setSiiresakiCd(this.siiresaki_cd);
//		****** DB Var3.6 修正箇所 *****発注NO.の追加**********************
		bean.setHachunoCd(this.hachuno_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setThemeCd(this.theme_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoNm(this.tenpo_nm);
		bean.setSuryoQt(this.suryo_qt);
		bean.setSuryoQtOld(this.suryo_qt_old);
		bean.setGentankaVl(this.gentanka_vl);
		bean.setBaitankaVl(this.baitanka_vl);
		bean.setColorCd(this.color_cd);
		bean.setSizeCd(this.size_cd);
		bean.setAsortPatternCd(this.asort_pattern_cd);
		bean.setHatyuDt(this.hatyu_dt);
		bean.setNohinDt(this.nohin_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
//		↓↓仕様変更（2005.07.07）↓↓
		bean.setSentaku(this.sentaku);
//		↑↑仕様変更（2005.07.07）↑↑
//		↓↓仕様変更（2005.10.14）↓↓
		bean.setHachutaniQt(this.hachutani_qt);
//		↑↑仕様変更（2005.10.14）↑↑
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
		if( !( o instanceof mst260101_SyokaiDonyuIryouBean ) ) return false;
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
