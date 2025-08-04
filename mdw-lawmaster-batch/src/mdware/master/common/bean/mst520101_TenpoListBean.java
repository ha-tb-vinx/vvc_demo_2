/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/02)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店マスタのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店マスタのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Nakajima
 * @version 1.0(2005/03/02)初版作成
 */
public class mst520101_TenpoListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

//  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	//public static final int TENPO_CD_MAX_LENGTH = 3;//店舗コードの長さ
//	public static final int KANJI_RN_MAX_LENGTH = 20;//略式名称(漢字)の長さ
	public static final int KANJI_NA_MAX_LENGTH = 80;//略式名称(漢字)の長さ
//  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑

	public static final int ADDRESS_KANJI_NA_MAX_LENGTH = 120;//住所(漢字)の長さ
	public static final int YUBIN_CD_MAX_LENGTH = 8;//郵便番号の長さ
	public static final int TEL_CD_MAX_LENGTH = 20;//電話番号の長さ

//    ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
	public static final int INSERT_TS_MAX_LENGTH = 8;//作成年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH = 82;//作成者社員IDの長さ
	public static final int UPDATE_TS_MAX_LENGTH = 8;//更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH = 82;//更新者社員IDの長さ
	public static final int INSERT_USER_NAME_MAX_LENGTH = 80;	//作成者MAXレングス
	public static final int UPDATE_USER_NAME_MAX_LENGTH = 80;	//更新者MAXレングス
//    ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑

	private String tenpo_cd = null;	//店舗コード
//  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
	//private String kanji_rn = null;	//略式名称(漢字)
	private String kanji_na = null;	//正式名称(漢字)
//  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑
	private String address_kanji_na = null;	//住所(漢字)
	private String yubin_cd = null;	//郵便番号
	private String tel_cd = null;	//電話番号

//  ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
	private String insert_ts = null;	//作成年月日
	private String insert_user_id = null;	//作成者社員ID
	private String update_ts = null;	//更新年月日
	private String update_user_id = null;	//更新者社員ID
	private String insert_user_name = null;	//作成者
	private String update_user_name = null;	//更新者
//  ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑

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
	 * mst520101_TenpoListBeanを１件のみ抽出したい時に使用する
	 */
	public static mst520101_TenpoListBean getRTENPOBean(DataHolder dataHolder)
	{
		mst520101_TenpoListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst520101_TenpoListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst520101_TenpoListBean)ite.next();
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

//  ↓↓2006.06.19 zhouzt カスタマイズ修正↓↓
	// kanji_rnに対するセッターとゲッターの集合
//	public boolean setKanjiRn(String kanji_rn)
//	{
//		this.kanji_rn = kanji_rn;
//		return true;
//	}
//	public String getKanjiRn()
//	{
//		return cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH);
//	}
//	public String getKanjiRnString()
//	{
//		return "'" + cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH) + "'";
//	}
//	public String getKanjiRnHTMLString()
//	{
//		return HTMLStringUtil.convert(cutString(this.kanji_rn,KANJI_RN_MAX_LENGTH));
//	}
	 //kanji_naに対するセッターとゲッターの集合
	public boolean setKanjiNa(String kanji_na)
	{
		this.kanji_na = kanji_na;
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
//  ↑↑2006.06.19 zhouzt カスタマイズ修正↑↑

	// address_kanji_naに対するセッターとゲッターの集合
	public boolean setAddressKanjiNa(String address_kanji_na)
	{
		this.address_kanji_na = address_kanji_na;
		return true;
	}
	public String getAddressKanjiNa()
	{
		return cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH);
	}
	public String getAddressKanjiNaString()
	{
		return "'" + cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH) + "'";
	}
	public String getAddressKanjiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.address_kanji_na,ADDRESS_KANJI_NA_MAX_LENGTH));
	}


	// yubin_cdに対するセッターとゲッターの集合
	public boolean setYubinCd(String yubin_cd)
	{
		this.yubin_cd = yubin_cd;
		return true;
	}
	public String getYubinCd()
	{
		return cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH);
	}
	public String getYubinCdString()
	{
		return "'" + cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH) + "'";
	}
	public String getYubinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yubin_cd,YUBIN_CD_MAX_LENGTH));
	}


	// tel_cdに対するセッターとゲッターの集合
	public boolean setTelCd(String tel_cd)
	{
		this.tel_cd = tel_cd;
		return true;
	}
	public String getTelCd()
	{
		return cutString(this.tel_cd,TEL_CD_MAX_LENGTH);
	}
	public String getTelCdString()
	{
		return "'" + cutString(this.tel_cd,TEL_CD_MAX_LENGTH) + "'";
	}
	public String getTelCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tel_cd,TEL_CD_MAX_LENGTH));
	}

//  ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
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
//  ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑

//  ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
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

	// insert_user_nameに対するセッターとゲッターの集合
	public boolean setInsertUserName(String insert_user_name)
	{
		this.insert_user_name = insert_user_name;
		return true;
	}
	public String getInsertUserName()
	{
		return cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH);
	}
	public String getInsertUserNameString()
	{
		return "'" + cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH) + "'";
	}
	public String getInsertUserNameHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.insert_user_name,INSERT_USER_NAME_MAX_LENGTH));
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

	// update_user_nameに対するセッターとゲッターの集合
	public boolean setUpdateUserName(String update_user_name)
	{
		this.update_user_name = update_user_name;
		return true;
	}
	public String getUpdateUserName()
	{
		return cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH);
	}
	public String getUpdateUserNameString()
	{
		return "'" + cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH) + "'";
	}
	public String getUpdateUserNameHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_user_name,UPDATE_USER_NAME_MAX_LENGTH));
	}
//  ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  tenpo_cd = " + getTenpoCdString()
			+ "  kanji_rn = " + getKanjiNaString()
			+ "  address_kanji_na = " + getAddressKanjiNaString()
			+ "  yubin_cd = " + getYubinCdString()
			+ "  tel_cd = " + getTelCdString()
//        ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
//        ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTENPOBean コピー後のクラス
	 */
	public mst520101_TenpoListBean createClone()
	{
		mst520101_TenpoListBean bean = new mst520101_TenpoListBean();
		bean.setTenpoCd(this.tenpo_cd);
//		bean.setKanJiRn(this.kanji_rn);
		bean.setKanjiNa(this.kanji_na);
		bean.setAddressKanjiNa(this.address_kanji_na);
		bean.setYubinCd(this.yubin_cd);
		bean.setTelCd(this.tel_cd);
//        ↓↓2006.06.15 zhouzt カスタマイズ修正↓↓
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
//        ↑↑2006.06.15 zhouzt カスタマイズ修正↑↑

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
		if( !( o instanceof mst520101_TenpoListBean ) ) return false;
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
