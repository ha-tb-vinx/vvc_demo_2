/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）単品店取扱マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する単品店取扱マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Kikuchi
 * @version 1.0(2005/03/24)初版作成
 */
public class mst680101_JidoHachuHonbuSuryoTeisiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TENPO_CD_MAX_LENGTH 			= 6;	// 店舗コード(FK)の長さ
	public static final int KANJI_NA_MAX_LENGTH			= 80;	// 漢字名称(正式)の長さ
	public static final int TENHANKU_CD_MAX_LENGTH 		= 4;	// 店販区コードの長さ
	public static final int HANKU_CD_MAX_LENGTH 			= 4;	// 販区コードの長さ
	public static final int SYOHIN_CD_MAX_LENGTH 		= 13;	// 商品コードの長さ
	public static final int YUKO_DT_MAX_LENGTH			= 8;	// 有効日の長さ
	public static final int TEISI_QT_MAX_LENGTH 			= 1;	// 停止数量の長さ
	public static final int CHIKAN_KB_MAX_LENGTH 		= 1;	// 置換区分の長さ
	public static final int INSERT_TS_MAX_LENGTH 		= 20;	// 作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH 	= 10;	// 作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH 		= 20;	// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH 	= 10;	// 更新者社員IDの長さ
	public static final int HENKO_DT_MAX_LENGTH 			= 8;	// 停止数量


	private String tenpo_cd 			= null;	// 店舗コード (FK)
	private String kanji_na			= null;	// 漢字名称(正式)
	private String tenhanku_cd			= null;	// 店販区コード
	private String hanku_cd 			= null;	//販区コード
	private String syohin_cd 			= null;	//商品コード
	private String yuko_dt 			= null;	// 有効日
//	private long teisi_qt				= 0;		// 停止数
	private double teisi_qt				= 0;		// 停止数
	private String chikan_kb		 	= null;	// 置換区分
	private String insert_ts 		  	= null;	//作成年月日
	private String insert_user_id 	  	= null;	//作成者社員ID
	private String update_ts 		  	= null;	//更新年月日
	private String update_user_id 	  	= null;	//更新者社員ID
//	private long teisi_suryo_qt		= 0;		// 停止数量
	private double teisi_suryo_qt		= 0;		// 停止数量
	
	private String sentaku 			= "";		//画面入力CheckBox
	private Map    ctlColor 			= null;	//画面コントロール色	
	private String disbale 			= null;	//明細入力可否

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
	 * mst680101_JidoHachuHonbuSuryoTeisiBeanを１件のみ抽出したい時に使用する
	 */
	public static mst680101_JidoHachuHonbuSuryoTeisiBean getmst310201_TanpinTenToriatukaiBean(DataHolder dataHolder)
	{
		mst680101_JidoHachuHonbuSuryoTeisiBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst680101_JidoHachuHonbuSuryoTeisiDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst680101_JidoHachuHonbuSuryoTeisiBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
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


	// kanji_naに対するセッターとゲッターの集合
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
		return true;
	}
	public String getKanjiNa()
	{
		return cutString(this.kanji_na, KANJI_NA_MAX_LENGTH);
	}
	public String getKanjiNaString()
	{
		return "'" + cutString(this.kanji_na, KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kanji_na, KANJI_NA_MAX_LENGTH));
	}

	// tenhanku_cdに対するセッターとゲッターの集合
	public boolean setTenHankuCd(String tenhanku_cd)
	{
		this.tenhanku_cd = tenhanku_cd;
		return true;
	}
	public String getTenHankuCd()
	{
		return cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH);
	}
	public String getTenHankuCdString()
	{
		return "'" + cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH) + "'";
	}
	public String getTenHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenhanku_cd, TENHANKU_CD_MAX_LENGTH));
	}


	// hanku_cdに対するセッターとゲッターの集合
	public boolean setHankuCd(String hanku_cd)
	{
		this.hanku_cd = hanku_cd;
		return true;
	}
	public String getHankuCd()
	{
		return cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH);
	}
	public String getHankuCdString()
	{
		return "'" + cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hanku_cd, HANKU_CD_MAX_LENGTH));
	}


	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd)
	{
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd()
	{
		return cutString(this.syohin_cd, SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString()
	{
		return "'" + cutString(this.syohin_cd, SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_cd, SYOHIN_CD_MAX_LENGTH));
	}




	// yuko_dtに対するセッターとゲッターの集合
	public boolean setYukoDt(String yuko_dt)
	{
		this.yuko_dt = yuko_dt;
		return true;
	}
	public String getYukoDt()
	{
		return cutString(this.yuko_dt, YUKO_DT_MAX_LENGTH);
	}
	public String getYukoDtString()
	{
		return "'" + cutString(this.yuko_dt, YUKO_DT_MAX_LENGTH) + "'";
	}
	public String getYukoDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_dt, YUKO_DT_MAX_LENGTH));
	}



	// chikan_kbに対するセッターとゲッターの集合
	public boolean setChikanKb(String chikan_kb)
	{
		this.chikan_kb = chikan_kb;
		return true;
	}
	public String getChikanKb()
	{
		return cutString(this.chikan_kb, CHIKAN_KB_MAX_LENGTH);
	}
	public String getChikanKbString()
	{
		return "'" + cutString(this.chikan_kb, CHIKAN_KB_MAX_LENGTH) + "'";
	}
	public String getChikanKbDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.chikan_kb, CHIKAN_KB_MAX_LENGTH));
	}


	// teisi_qtに対するセッターとゲッターの集合
	public boolean setTeisiQt(String teisi_qt)
	{
		try
		{
			this.teisi_qt = Double.parseDouble(teisi_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeisiQt(double teisi_qt)
	{
		this.teisi_qt = teisi_qt;
		return true;
	}
	public double getTeisiQt()
	{
		return this.teisi_qt;
	}
	public String getTeisiQtString()
	{
		return Double.toString(this.teisi_qt);
	}


	// teisi_suryo_qtに対するセッターとゲッターの集合
	public boolean setTeisiSuryoQt(String teisi_suryo_qt)
	{
		try
		{
			this.teisi_suryo_qt = Double.parseDouble(teisi_suryo_qt);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTeisiSuryoQt(double teisi_suryo_qt)
	{
		this.teisi_suryo_qt = teisi_suryo_qt;
		return true;
	}
	public double getTeisiSuryoQt()
	{
		return this.teisi_suryo_qt;
	}
	public String getTeisiSuryoQtString()
	{
		return Double.toString(this.teisi_suryo_qt);
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
	public boolean setUpdateUserTs(String update_user_id)
	{
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserTs()
	{
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserTsString()
	{
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}


	
	
	// sentakuに対するセッターとゲッターの集合
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	public String getSentaku()
	{
		return this.sentaku;
	}

	// ctlColorに対するセッターとゲッターの集合
	public boolean setCtlColor(Map ctlColor)
	{
		this.ctlColor = ctlColor;
		return true;
	}
	public Map getCtlColor()
	{
		return this.ctlColor;
	}
	
	// disbaleに対するセッターとゲッターの集合
	public boolean setDisbale(String disbale)
	{
		this.disbale = disbale;
		return true;
	}
	public String getDisbale()
	{
		return this.disbale;
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  kanji_na = " + getKanjiNaString()
			+ "  tenhanku_cd = " + getTenHankuCdString()
			+ "  syohin_cd = " + getSyohinCdString()
			+ "  yuko_dt = " + getYukoDtString()
			+ "  teisi_qt = " + getTeisiQtString()
			+ "  chikan_kb = " + getChikanKbString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserTsString()
			+ "  teisi_suryo_qt = " + getTeisiSuryoQtString()
			+ " createDatabase  = " + createDatabase;
	}
	
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst310201_TanpinTenToriatukaiBean コピー後のクラス
	 */
	public mst680101_JidoHachuHonbuSuryoTeisiBean createClone()
	{
		mst680101_JidoHachuHonbuSuryoTeisiBean bean = new mst680101_JidoHachuHonbuSuryoTeisiBean();
		bean.setTenpoCd(this.tenpo_cd);
		bean.setKanjiNa(this.kanji_na);
		bean.setHankuCd(this.hanku_cd);
		bean.setTenHankuCd(this.tenhanku_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setYukoDt(this.yuko_dt);
		bean.setTeisiQt(this.teisi_qt);
		bean.setChikanKb(this.chikan_kb);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserTs(this.update_user_id);
		bean.setTeisiSuryoQt(Double.toString(this.teisi_suryo_qt));
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
		if( !( o instanceof mst680101_JidoHachuHonbuSuryoTeisiBean ) ) return false;
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
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
