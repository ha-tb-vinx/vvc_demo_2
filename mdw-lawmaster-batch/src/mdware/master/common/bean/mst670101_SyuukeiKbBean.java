/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）集計種別マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する集計種別マスタのレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC T.Kimura
 * @version 1.0(2005/05/09)初版作成
 */
public class mst670101_SyuukeiKbBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SENTAKU_MAX_LENGTH = 1;
	
	public static final int SYUKEI_KB_CD_MAX_LENGTH = 6;
	public static final int SYUKEI_KB_NA_MAX_LENGTH = 36;
	public static final int SYUKEI_KB_KA_MAX_LENGTH = 18;
	public static final int INSERT_TS_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 20;
	public static final int SYORISYA_USER_ID_MAX_LENGTH = 10;
	public static final int SYORISYA_USER_NA_MAX_LENGTH = 20;

	private String sentaku = null;					//処理選択
    private String InsertData = null; // 追加処理判定

	private String syukei_kb_cd = null;
	private String syukei_kb_na = null;
	private String syukei_kb_ka = null;
	private String insert_ts = null;
	private String update_ts = null;
	private String syorisya_user_id = null;
	private String syorisya_user_na = null;

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase()
	{
		createDatabase = true;
	}
	/**  ＤＢ更新を行う時に役に立つフラグ 	 
	 * @return createDatabase createDatabase
	 */ 
	public boolean isCreateDatabase()
	{
		return createDatabase;
	}

	/**
	 * mst630101_SyuukeiSyubetuBeanを１件のみ抽出したい時に使用する
	 * @param dataHolder dataHolder
	 * @return mst630101_SyuukeiSyubetuBean mst630101_SyuukeiSyubetuBean
	 */
	public static mst630101_SyuukeiSyubetuBean getmst630101_SyuukeiSyubetuBean(DataHolder dataHolder)
	{
		mst630101_SyuukeiSyubetuBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst630101_SyuukeiSyubetuDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst630101_SyuukeiSyubetuBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}

	/**  syukei_kb_cdに対するセッター
	 * @param syukei_kb_cd syukei_kb_cd
	 * @return boolean
	 */
	public boolean setSyukeiKbCd(String syukei_kb_cd)
	{
		this.syukei_kb_cd = syukei_kb_cd;
		return true;
	}
    /** syukei_kb_cdに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbCd()
	{
		return cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH);
	}
    /** syukei_kb_cdに対するゲッター 	
     * @return boolean
     */
	public String getSyukeiKbCdString()
	{
		return "'" + cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH) + "'";
	}
    /** syukei_kb_cdに対するゲッター 
     * 	 * @return boolean
     */
	public String getSyukeiKbCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_cd,SYUKEI_KB_CD_MAX_LENGTH));
	}


	/**  syukei_kb_naに対するセッター
	 * @param syukei_kb_na syukei_kb_na
	 * @return boolean
	 */
	public boolean setSyukeiKbNa(String syukei_kb_na)
	{
		this.syukei_kb_na = syukei_kb_na;
		return true;
	}
    /** syukei_kb_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbNa()
	{
		return cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH);
	}
    /** syukei_kb_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbNaString()
	{
		return "'" + cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH) + "'";
	}
    /** syukei_kb_naに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_na,SYUKEI_KB_NA_MAX_LENGTH));
	}


	/**  syukei_kb_kaに対するセッター
	 * @param syukei_kb_ka kanji_na
	 * @return boolean
	 */
	public boolean setSyukeiKbKa(String syukei_kb_ka)
	{
		this.syukei_kb_ka = syukei_kb_ka;
		return true;
	}
    /** syukei_kb_kaに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbKa()
	{
		return cutString(this.syukei_kb_ka,SYUKEI_KB_KA_MAX_LENGTH);
	}
    /** syukei_kb_kaに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbKaString()
	{
		return "'" + cutString(this.syukei_kb_ka,SYUKEI_KB_KA_MAX_LENGTH) + "'";
	}
    /** syukei_kb_kaに対するゲッター 
	 * @return boolean
     */
	public String getSyukeiKbKaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syukei_kb_ka,SYUKEI_KB_KA_MAX_LENGTH));
	}


	/**  insert_tsに対するセッター
	 * @param insert_ts insert_ts
	 * @return boolean
	 */
	public boolean setInsertTs(String insert_ts)
	{
		this.insert_ts = insert_ts;
		return true;
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTs()
	{
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTsString()
	{
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
    /** insert_tsに対するゲッター 
	 * @return boolean
     */
	public String getInsertTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}


	/**  update_tsに対するセッター
	 * @param update_ts update_ts
	 * @return boolean
	 */
	public boolean setUpdateTs(String update_ts)
	{
		this.update_ts = update_ts;
		return true;
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTs()
	{
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsString()
	{
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
    /** update_tsに対するゲッター 
	 * @return boolean
     */
	public String getUpdateTsHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}


	/**  syorisya_user_idに対するセッター
	 * @param syorisya_user_id syorisya_user_id
	 * @return boolean
	 */
	public boolean setSyorisyaUserId(String syorisya_user_id)
	{
		this.syorisya_user_id = syorisya_user_id;
		return true;
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserId()
	{
		return cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH);
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserIdString()
	{
		return "'" + cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH) + "'";
	}
	/** syorisya_user_idに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_id,SYORISYA_USER_ID_MAX_LENGTH));
	}
	
	
	/**  syorisya_user_naに対するセッター
	 * @param syorisya_user_na syorisya_user_na
	 * @return boolean
	 */
	public boolean setSyorisyaUserNa(String syorisya_user_na)
	{
		this.syorisya_user_na = syorisya_user_na;
		return true;
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNa()
	{
		return cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH);
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNaString()
	{
		return "'" + cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH) + "'";
	}
	/** syorisya_user_naに対するゲッター 
	 * @return boolean
	 */
	public String getSyorisyaUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syorisya_user_na,SYORISYA_USER_NA_MAX_LENGTH));
	}
	
	
	/**  sentakuに対するセッター
	 * @param sentaku sentaku
	 * @return boolean
	 */
	public boolean setSentaku(String sentaku)
	{
		this.sentaku = sentaku;
		return true;
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentaku()
	{
		return cutString(this.sentaku,SENTAKU_MAX_LENGTH);
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentakuString()
	{
		return "'" + cutString(this.sentaku,SENTAKU_MAX_LENGTH) + "'";
	}
	/** sentakuに対するゲッター 
	 * @return boolean
	 */
	public String getSentakuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.sentaku,SENTAKU_MAX_LENGTH));
	}
	

    /**
     * @return InsertData を戻します。
     */
    public String getInsertData() {
        return InsertData;
    }

    /**
     * @param InsertData
     *            InsertData を設定。
     */
    public void setInsertData(String insertData) {
        this.InsertData = insertData;
    }
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  syukei_kb_cd = " + getSyukeiKbCdString()
			+ "  syukei_kb_na = " + getSyukeiKbNaString()
			+ "  syukei_kb_ka = " + getSyukeiKbKaString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  syorisya_user_id = " + getSyorisyaUserIdString()
			+ "  syorisya_user_na = " + getSyorisyaUserNaString()			
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return mst630101_SyuukeiSyubetuBean コピー後のクラス
	 */
	public mst670101_SyuukeiKbBean createClone()
	{
		mst670101_SyuukeiKbBean bean = new mst670101_SyuukeiKbBean();
		bean.setSyukeiKbCd(this.syukei_kb_cd);
		bean.setSyukeiKbNa(this.syukei_kb_na);
		bean.setSyukeiKbKa(this.syukei_kb_ka);
		bean.setInsertTs(this.insert_ts);
		bean.setUpdateTs(this.update_ts);
		bean.setSyorisyaUserId(this.syorisya_user_id);
		bean.setSyorisyaUserNa(this.syorisya_user_na);
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}
	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param o 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst670101_SyuukeiKbBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param base カットされる文字列
	 * @param max 最大バイト数
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
