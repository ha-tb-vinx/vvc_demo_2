/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst290101_SetSyohinマスタのレコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst290101_SetSyohinマスタのレコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

//*****　明細行格納用　****************************************************************
public class mst290101_SetSyohinBean {
	
	private static final StcLog stcLog = StcLog.getInstance();

	//定数設定
	public static final int HANKU_CD_MAX_LENGTH = 4;		        // 販区コードの長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;		    // 販区名正式名称(漢字）
	public static final int HINMEI_KANJI_NA_MAX_LENGTH = 80;		// 漢字品名
	public static final int SYOHIN_1_CD_MAX_LENGTH = 13;			// 商品コード1
	public static final int HONSU_1_QT_MAX_LENGTH = 2;			// 本数1
	
	public static final int INSERT_TS_MAX_LENGTH = 20;			// 作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 10;		// 作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 20;			// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 10;		// 更新者社員IDの長さ
	public static final int DELETE_FG_MAX_LENGTH = 1;			// 削除フラグの長さ
	public static final int SENTAKU_MAX_LENGTH = 1;				//処理選択
		
	//明細行
	private String hanku_cd = null;       		//販区コード1
	private String kanji_na = null;               //販区名正式名称（漢字）
	private String hinmei_kanji_na = null;		//商品名
	private String syohin_1_cd = null;			//商品コード１
	private String honsu_1_qt = null;				//本数１

	private String insert_ts = null;	    		//作成年月日
	private String insert_user_id = null;			//作成者社員ID
	private String insert_user_na = null;			//作成者名
	private String update_ts = null;  			//更新年月日
	private String update_user_id = null;			//更新者社員ID
	private String update_user_na = null;			//更新者名
	private String delete_fg = null;			    //削除フラグ
	private String sentaku = "0";					//処理選択


	/**
	 *ゲッター、セッター集合
	 */

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
	public static mst290101_SetSyohinBean getRKeiryokiBean(DataHolder dataHolder)
	{
		mst290101_SetSyohinBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst290101_SetSyohinDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst290101_SetSyohinBean)ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	// hanku_cd1に対するセッターとゲッターの集合
	// 販区コード
	public boolean setHankuCd(String keiryo_hanku_cd)
	{
		this.hanku_cd = keiryo_hanku_cd;
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

	// kanji_naに対するセッターとゲッターの集合
    //	販区名正式名称（漢字）
	public boolean setKanjiNa(String syohin_yobidasi)
	{
		this.kanji_na = syohin_yobidasi;
		return true;
	}
	public String getKanjiNa()
	{
		return cutString(this.kanji_na,KANJI_NA_MAX_LENGTH);
	}
	public String getKanjiNaString()
	{
		return "'" + cutString(this.kanji_na,KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na,KANJI_NA_MAX_LENGTH));
	}


     //	hinmei_kanji_naに対するセッターとゲッターの集合
	 //	商品名
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
	 
	 //	syohin_1_cdに対するセッターとゲッターの集合
	 //	セット商品コード1
	 public boolean setSyohin1Cd(String setSyohin1Cd)
	 {
		 this.syohin_1_cd = setSyohin1Cd;
		 return true;
	 }
	 public String getSyohin1Cd()
	 {
		 return cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH);
	 }
	 public String getSyohin1CdString()
	 {
		 return "'" + cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH) + "'";
	 }
	 public String getSyohin1CdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.syohin_1_cd,SYOHIN_1_CD_MAX_LENGTH));
	 }


	//	honsu_1_qtに対するセッターとゲッターの集合
	//	本数1
	public boolean setHonsu1Qt(String honsu_1_qt)
	{
		this.honsu_1_qt = honsu_1_qt;
		return true;
	}
	public String getHonsu1Qt()
	{
		return cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH);
	}
	public String getHonsu1QtString()
	{
		return "'" + cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH) + "'";
	}
	public String getHonsu1QtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.honsu_1_qt,HONSU_1_QT_MAX_LENGTH));
	}

	//insert_tsに対するセッターとゲッターの集合
	//登録日
	public boolean setInsertTs(String s_gyosyu_cd)
	{
		this.insert_ts = s_gyosyu_cd;
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
	
	//	insert_user_idに対するセッターとゲッターの集合
	//	登録者名
	public boolean setInsertUserId(String s_gyosyu_cd)
	{
		this.insert_user_id = s_gyosyu_cd;
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
	
	//	update_tsに対するセッターとゲッターの集合
	//	更新日
	public boolean setUpdateTs(String s_gyosyu_cd)
	{
		this.update_ts = s_gyosyu_cd;
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
	
	//	update_user_idに対するセッターとゲッターの集合
	//	更新者名
	public boolean setUpdateUserId(String s_gyosyu_cd)
	{
		this.update_user_id = s_gyosyu_cd;
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
	
	//delete_fgに対するセッターとゲッターの集合
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

	//update_user_naに対するセッターとゲッターの集合
	//更新者名
	public boolean setUpdateUserNa(String update_user_na)
	{
		this.update_user_id = update_user_na;
		return true;
	}
	public String getUpdateUserNa()
	{
		return cutString(this.update_user_na,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserNaString()
	{
		return "'" + cutString(this.update_user_na,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_na,UPDATE_USER_ID_MAX_LENGTH));
	}

	//insert_user_naに対するセッターとゲッターの集合
	//登録者名
	public boolean setInsertUserNa(String insert_user_na)
	{
		this.update_user_id = insert_user_na;
		return true;
	}
	public String getInsertUserNa()
	{
		return cutString(this.insert_user_na,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserNaString()
	{
		return "'" + cutString(this.insert_user_na,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserNaNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_na,UPDATE_USER_ID_MAX_LENGTH));
	}	


	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return   "  hanku_cd = "        + getHankuCdString()
				+ "  kanji_na = "        + getKanjiNaString()
				+ "  hinmei_kanji_na = " + getHinmeiKanjiNaString()
				+ "  syohin_1_cd = "     + getSyohin1CdString()
				+ "  honsu_1_qt = "      + getHonsu1QtString()
				+ "  insert_ts = "       + getInsertTsString()
				+ "  insert_user_id =  " + getInsertUserIdString()
				+ "  insert_user_na =  " + getInsertUserNaString()
				+ "  update_ts = "       + getUpdateTsString()
				+ "  update_user_id = "  + getUpdateUserIdString()
				+ "  update_user_na = "  + getUpdateUserNaString()
				+ "  sentaku = "         + getSentakuString()
				+ "  delete_fg = "       + getDeleteFgString()
				+ " createDatabase  = "  + createDatabase;
	}
	

	
		
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RKeiryokiBean コピー後のクラス
	 */
//	public mst290101_SetSyohinBean createClone()
//	{
////		mst290101_SetSyohinBean bean = new mst290101_SetSyohinBean();
////		bean.setKeiryoHankuCd(this.keiryo_hanku_cd);
////		bean.setSyohinYobidasi(this.syohin_yobidasi);
////		bean.setSGyosyuCd(this.s_gyosyu_cd);
////		bean.setThemeCd(this.theme_cd);
////		bean.setHaneiDt(this.hanei_dt);
////		bean.setSyohinKbn(this.syohin_kbn);
////		bean.setSyohinCd(this.syohin_cd);
////		
////		if( this.isCreateDatabase() ) bean.setCreateDatabase();
////		return bean;
////	}



	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst290101_SetSyohinBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RKeiryokiBean コピー後のクラス
	 */
	public mst290101_SetSyohinBean createClone()
	{
		mst290101_SetSyohinBean bean = new mst290101_SetSyohinBean();
		
		bean.setHankuCd(this.hanku_cd);				//販区コード
		bean.setHankuCd(this.kanji_na);				//販区名
		bean.setHinmeiKanjiNa(this.hinmei_kanji_na);	//商品名
		bean.setSyohin1Cd(this.syohin_1_cd);			//セット商品コード
		bean.setHonsu1Qt(this.honsu_1_qt);				//本数
		
		bean.setInsertTs(this.insert_ts);				//作成年月日
		bean.setInsertUserId(this.insert_user_id);		//作成者社員ID
		bean.setInsertUserNa(this.insert_user_na);		//作成者名
		bean.setUpdateTs(this.update_ts);				//更新年月日
		bean.setUpdateUserId(this.update_user_id);		//更新者社員ID
		bean.setUpdateUserNa(this.update_user_na);		//更新者名
		bean.setSentaku(this.sentaku);					//処理選択	
		bean.setDeleteFg(this.delete_fg);				//削除フラグ
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
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
