/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）自動採番枠品種別マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する自動採番枠品種別マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius K.Inamoto
 * @version 1.0(2005/03/17)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: mst660101_TanpinJidoSaibanBean</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Bean Creator(2005.03.22) Version 1.0
 * @version X.X (Create time: 2004/3/22 15:55:58)
 */
public class mst660101_TanpinJidoSaibanBean
{
	private static final StcLog stcLog = StcLog.getInstance();
	
	public static final int P_KANRI_KB_MAX_LENGTH = 1;		//管理区分の長さ
	public static final int HANKU_CD_MAX_LENGTH   = 4;		//販区コードの長さ
	public static final int HINSYU_CD_MAX_LENGTH  = 4;		//品種コードの長さ
	public static final int HINSYU_KANJI_NA_MAX_LENGTH = 80;	//販区名の長さ
	public static final int HANKU_KANJI_NA_MAX_LENGTH  = 80;	//品種名の長さ
	public static final int START_TANPIN_CD_MAX_LENGTH = 3;	//開始単品コードの長さ
	public static final int END_TANPIN_CD_MAX_LENGTH   = 3;	//終了単品コードの長さ
	public static final int HINSYU_CHECK_CD_MAX_LENGTH = 4;	//確定判断用品種コードの長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 7;	//確定判断用品種コードの長さ
	
	private String p_kanri_kb   = null;		//管理区分
	private String hanku_cd     = null;		//販区コード
	private String hinsyu_cd    = null;		//品種コード
	private String hinsyu_kanji_na     = null;//品種名
	private String hanku_kanji_na     = null;	//販区名
	private String start_tanpin_cd = null;	//開始単品コード
	private String end_tanpin_cd = null;		//終了単品コード
	private String hinsyu_check_cd = null;	//更新判断用品種コード
	private String insert_ts		= null;	//作成者ＩＤ
	private String insert_user_id  = null;	//作成年月日
	private String update_ts		= null;	//更新者ＩＤ
	private String update_user_id  = null;	//更新年月日
	
	public static final int DB_HANKU_CD_MAX_LENGTH				 = 4;//販区コードの長さ
	private String db_hanku_cd = null;//販区コード
	
	// db_hanku_cdに対するセッターとゲッターの集合
	public boolean setDbHankuCd(String db_hanku_cd)
	{
		this.db_hanku_cd = db_hanku_cd;
		return true;
	}
	public String getDbHankuCd()
	{
		return cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH);
	}
	public String getDbHankuCdString()
	{
		return "'" + cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH) + "'";
	}
	public String getDbHankuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.db_hanku_cd, DB_HANKU_CD_MAX_LENGTH));
	}
	
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
	 * TanpinJidoSaibanBeanを１件のみ抽出したい時に使用する
	 */
	public static mst660101_TanpinJidoSaibanBean getHaisosakiSelBean(DataHolder dataHolder)
	{
		mst660101_TanpinJidoSaibanBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst660101_TanpinJidoSaibanDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if ( ite.hasNext() )
				bean = ( mst660101_TanpinJidoSaibanBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if ( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}
	 
	 
     //	p_kanri_kbに対するセッターとゲッターの集合
	 public boolean setPKanriKb(String kanri_kb)
	 {
		 this.p_kanri_kb = kanri_kb;
		 return true;
	 }
	 public String getPKanriKb()
	 {
		 return cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH);
	 }
	 public String getPKanriKbString()
	 {
		 return "'" + cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH) + "'";
	 }
	 public String getPKanriKbHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.p_kanri_kb, P_KANRI_KB_MAX_LENGTH));
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
	
	
	 //	hinsyu_cdに対するセッターとゲッターの集合
	 public boolean setHinsyuCd(String hinsyu_cd)
	 {
		 this.hinsyu_cd = hinsyu_cd;
		 return true;
	 }
	 public String getHinsyuCd()
	 {
		 return cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH);
	 }
	 public String getHinsyuCdString()
	 {
		 return "'" + cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH) + "'";
	 }
	 public String getHinsyuCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hinsyu_cd, HINSYU_CD_MAX_LENGTH));
	 }
	 
	 
	 //	hinsyu_kanji_naに対するセッターとゲッターの集合
	 public boolean setHinsyuKanjiNa(String hinsyu_kanji_na)
	 {
		 this.hinsyu_kanji_na = hinsyu_kanji_na;
		 return true;
	 }
	 public String getHinsyuKanjiNa()
	 {
		 return cutString(this.hinsyu_kanji_na, HINSYU_KANJI_NA_MAX_LENGTH);
	 }
	 public String getHinsyuKanjiNaString()
	 {
		 return "'" + cutString(this.hinsyu_kanji_na, HINSYU_KANJI_NA_MAX_LENGTH) + "'";
	 }
	 public String getHinsyuKanjiNaHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hinsyu_kanji_na, HINSYU_KANJI_NA_MAX_LENGTH));
	 }
	 
	 
	 //	hanku_kanji_naに対するセッターとゲッターの集合
	 public boolean setHankuKanjiNa(String hanku_kanji_na)
	 {
		 this.hanku_kanji_na = hanku_kanji_na;
		 return true;
	 }
	 public String getHankuKanjiNa()
	 {
		 return cutString(this.hanku_kanji_na, HANKU_KANJI_NA_MAX_LENGTH);
	 }
	 public String getHankuKanjiNaString()
	 {
		 return "'" + cutString(this.hanku_kanji_na, HANKU_KANJI_NA_MAX_LENGTH) + "'";
	 }
	 public String getHankuKanjiNaHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hanku_kanji_na, HANKU_KANJI_NA_MAX_LENGTH));
	 }
	 
	 
	 //	start_tanpin_cdに対するセッターとゲッターの集合
	 public boolean setStartTanpinCd(String start_tanpin_cd)
	 {
		 this.start_tanpin_cd = start_tanpin_cd;
		 return true;
	 }
	 public String getStartTanpinCd()
	 {
		 return cutString(this.start_tanpin_cd, START_TANPIN_CD_MAX_LENGTH);
	 }
	 public String getStartTanpinCdString()
	 {
		 return "'" + cutString(this.start_tanpin_cd, START_TANPIN_CD_MAX_LENGTH) + "'";
	 }
	 public String getStartTanpinCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.start_tanpin_cd, START_TANPIN_CD_MAX_LENGTH));
	 }
	 
	 
	 //	end_tanpin_cdに対するセッターとゲッターの集合
	 public boolean setEndTanpinCd(String end_tanpin_cd)
	 {
		 this.end_tanpin_cd = end_tanpin_cd;
		 return true;
	 }
	 public String getEndTanpinCd()
	 {
		 return cutString(this.end_tanpin_cd, END_TANPIN_CD_MAX_LENGTH);
	 }
	 public String getEndTanpinCdString()
	 {
		 return "'" + cutString(this.end_tanpin_cd, END_TANPIN_CD_MAX_LENGTH) + "'";
	 }
	 public String getEndTanpinCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.end_tanpin_cd, END_TANPIN_CD_MAX_LENGTH));
	 }
	 
	 
	 //	hinsyu_check_cdに対するセッターとゲッターの集合
	 public boolean setHinsyuCheckCd(String hinsyu_check_cd)
	 {
		 this.hinsyu_check_cd = hinsyu_check_cd;
		 return true;
	 }
	 public String getHinsyuCheckCd()
	 {
		 return cutString(this.hinsyu_check_cd, HINSYU_CHECK_CD_MAX_LENGTH);
	 }
	 public String getHinsyuCheckCdString()
	 {
		 return "'" + cutString(this.hinsyu_check_cd, HINSYU_CHECK_CD_MAX_LENGTH) + "'";
	 }
	 public String getHinsyuCheckCdHTMLString()
	 {
		 return HTMLStringUtil.convert(cutString(this.hinsyu_check_cd, HINSYU_CHECK_CD_MAX_LENGTH));
	 }
	 
	/**
	 * 更新年月日に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateTs("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setInsertTs(String insert_ts)
		{
			this.insert_ts = insert_ts;
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
		public String getInsertTs()
		{
			return this.insert_ts;
		}

	/**
	 * 更新者社員IDに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setUpdateUserId("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
		public boolean setInsertUserId(String insert_user_id)
		{
			this.insert_user_id = insert_user_id;
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
		public String getUpdateUserIdHTMLString()
		{
			return HTMLStringUtil.convert(cutString(this.update_user_id, UPDATE_USER_ID_MAX_LENGTH));
		}
	 
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return  "  p_kanri_kb = " + getPKanriKbString()
			+ "  hanku_cd = " + getHankuCdString()
			+ "  hinsyu_cd = " + getHinsyuCdString()
			+ "  hinsyu_kanji_na = " + getHinsyuKanjiNaString()
			+ "  hanku_kanji_na = " + getHankuKanjiNaString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return HinsyuSubSearchBean コピー後のクラス
	 */
	public mst660101_TanpinJidoSaibanBean createClone()
	{
		mst660101_TanpinJidoSaibanBean bean = new mst660101_TanpinJidoSaibanBean();
		bean.setPKanriKb(this.p_kanri_kb);
		bean.setHankuCd(this.hanku_cd);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHankuKanjiNa(this.hinsyu_kanji_na);
		bean.setHankuKanjiNa(this.hanku_kanji_na);
		if ( this.isCreateDatabase() ) bean.setCreateDatabase();
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
		if ( o == null ) return false;
		if ( !( o instanceof mst660101_TanpinJidoSaibanBean ) ) return false;
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
		if ( base == null ) return null;
		String wk = "";
		for ( int pos = 0, count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos, pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if ( count > max )
					break;
				wk += base.substring(pos, pos+1);
			}
			catch (Exception e)
			{
//BUGNO-S051 2005.05.15 Sirius START
//				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos, pos+1) + "をShift_JISに変換できませんでした。");
				stcLog.getLog().error(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos, pos+1) + "をShift_JISに変換できませんでした。");
//BUGNO-S051 2005.05.15 Sirius END

			}
		}
		return wk;
	}
}
