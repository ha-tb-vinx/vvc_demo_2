/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）操作者テーブルのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する操作者テーブルのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/02/26)初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）操作者テーブルのレコード格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する操作者テーブルのレコード格納用クラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author Sirius Murata
 * @version 1.0(2005/02/26)初版作成
 */
public class mst000501_SysSosasyaBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int USER_ID_MAX_LENGTH = 7;//ユーザIDの長さ
	public static final int USER_PASSWORD_MAX_LENGTH = 7;//ユーザパスワードの長さ
	public static final int HOJIN_CD_MAX_LENGTH = 5;//法人コードの長さ
	public static final int GYOSYU_CD_MAX_LENGTH = 4;//業種コードの長さ
	public static final int S_GYOSYU_CD_MAX_LENGTH = 4;//小業種コードの長さ
	public static final int URIKU1_CD_MAX_LENGTH = 4;//売区1コードの長さ
	public static final int URIKU2_CD_MAX_LENGTH = 4;//売区2コードの長さ
	public static final int URIKU3_CD_MAX_LENGTH = 4;//売区3コードの長さ
	public static final int KENGEN_CD_MAX_LENGTH = 2;//権限コードの長さ
	public static final int TENPO_CD_MAX_LENGTH = 6;//店舗コードの長さ
	public static final int USER_NA_MAX_LENGTH = 20;//ユーザ氏名の長さ
	public static final int YUKO_START_DT_MAX_LENGTH = 8;//有効開始日の長さ
	public static final int YUKO_END_DT_MAX_LENGTH = 8;//有効終了日の長さ
	public static final int IP_ADDRESS_MAX_LENGTH = 12;//IPアドレスの長さ
	public static final int UPDATE_DT_MAX_LENGTH = 8;//更新日の長さ

	private String user_id = null;	//ユーザID
	private String user_password = null;	//ユーザパスワード
	private String hojin_cd = null;	//法人コード
	private String gyosyu_cd = null;	//業種コード
	private String s_gyosyu_cd = null;	//小業種コード
	private String uriku1_cd = null;	//売区1コード
	private String uriku2_cd = null;	//売区2コード
	private String uriku3_cd = null;	//売区3コード
	private String kengen_cd = null;	//権限コード
	private String tenpo_cd = null;	//店舗コード
	private String user_na = null;	//ユーザ氏名
	private String yuko_start_dt = null;	//有効開始日
	private String yuko_end_dt = null;	//有効終了日
	private String ip_address = null;	//IPアドレス
	private String update_dt = null;	//更新日


	private String sosasya_gyosyu = null;
	private String gyosyu_menu_iryo_flg = null;
	private String gyosyu_menu_liv_flg = null;
	private String gyosyu_menu_gro_flg = null;
	private String gyosyu_menu_fresh_flg = null;
	private String selected_gyosyu_cd = null;

	private String mi_kakunin = "1";
	private String error = "1";

	// ↓↓　2006/04/18 kim START
	private String posTenpoSonZai_fg =null;	// POS管理向けの店舗コード存在フラッグ
	// ↑↑　2006/04/18 kim END

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
	 * SysSosasyaBeanを１件のみ抽出したい時に使用する
	 */
	public static mst000501_SysSosasyaBean getSysSosasyaBean(DataHolder dataHolder)
	{
		mst000501_SysSosasyaBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst000501_SysSosasyaDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst000501_SysSosasyaBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}




	// user_idに対するセッターとゲッターの集合
	public boolean setUserId(String user_id)
	{
		this.user_id = user_id;
		return true;
	}
	public String getUserId()
	{
		return cutString(this.user_id,USER_ID_MAX_LENGTH);
	}
	public String getUserIdString()
	{
		return "'" + cutString(this.user_id,USER_ID_MAX_LENGTH) + "'";
	}
	public String getUserIdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_id,USER_ID_MAX_LENGTH));
	}


	// user_passwordに対するセッターとゲッターの集合
	public boolean setUserPassword(String user_password)
	{
		this.user_password = user_password;
		return true;
	}
	public String getUserPassword()
	{
		return cutString(this.user_password,USER_PASSWORD_MAX_LENGTH);
	}
	public String getUserPasswordString()
	{
		return "'" + cutString(this.user_password,USER_PASSWORD_MAX_LENGTH) + "'";
	}
	public String getUserPasswordHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_password,USER_PASSWORD_MAX_LENGTH));
	}


	// hojin_cdに対するセッターとゲッターの集合
	public boolean setHojinCd(String hojin_cd)
	{
		this.hojin_cd = hojin_cd;
		return true;
	}
	public String getHojinCd()
	{
		return cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH);
	}
	public String getHojinCdString()
	{
		return "'" + cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH) + "'";
	}
	public String getHojinCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hojin_cd,HOJIN_CD_MAX_LENGTH));
	}


	// gyosyu_cdに対するセッターとゲッターの集合
	public boolean setGyosyuCd(String gyosyu_cd)
	{
		this.gyosyu_cd = gyosyu_cd;
		return true;
	}
	public String getGyosyuCd()
	{
		return cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH);
	}
	public String getGyosyuCdString()
	{
		return "'" + cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.gyosyu_cd,GYOSYU_CD_MAX_LENGTH));
	}


	// s_gyosyu_cdに対するセッターとゲッターの集合
	public boolean setSGyosyuCd(String s_gyosyu_cd)
	{
		this.s_gyosyu_cd = s_gyosyu_cd;
		return true;
	}
	public String getSGyosyuCd()
	{
		return cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH);
	}
	public String getSGyosyuCdString()
	{
		return "'" + cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH) + "'";
	}
	public String getSGyosyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.s_gyosyu_cd,S_GYOSYU_CD_MAX_LENGTH));
	}


	// uriku1_cdに対するセッターとゲッターの集合
	public boolean setUriku1Cd(String uriku1_cd)
	{
		this.uriku1_cd = uriku1_cd;
		return true;
	}
	public String getUriku1Cd()
	{
		return cutString(this.uriku1_cd,URIKU1_CD_MAX_LENGTH);
	}
	public String getUriku1CdString()
	{
		return "'" + cutString(this.uriku1_cd,URIKU1_CD_MAX_LENGTH) + "'";
	}
	public String getUriku1CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku1_cd,URIKU1_CD_MAX_LENGTH));
	}


	// uriku2_cdに対するセッターとゲッターの集合
	public boolean setUriku2Cd(String uriku2_cd)
	{
		this.uriku2_cd = uriku2_cd;
		return true;
	}
	public String getUriku2Cd()
	{
		return cutString(this.uriku2_cd,URIKU2_CD_MAX_LENGTH);
	}
	public String getUriku2CdString()
	{
		return "'" + cutString(this.uriku2_cd,URIKU2_CD_MAX_LENGTH) + "'";
	}
	public String getUriku2CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku2_cd,URIKU2_CD_MAX_LENGTH));
	}


	// uriku3_cdに対するセッターとゲッターの集合
	public boolean setUriku3Cd(String uriku3_cd)
	{
		this.uriku3_cd = uriku3_cd;
		return true;
	}
	public String getUriku3Cd()
	{
		return cutString(this.uriku3_cd,URIKU3_CD_MAX_LENGTH);
	}
	public String getUriku3CdString()
	{
		return "'" + cutString(this.uriku3_cd,URIKU3_CD_MAX_LENGTH) + "'";
	}
	public String getUriku3CdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.uriku3_cd,URIKU3_CD_MAX_LENGTH));
	}


	// kengen_cdに対するセッターとゲッターの集合
	public boolean setKengenCd(String kengen_cd)
	{
		this.kengen_cd = kengen_cd;
		return true;
	}
	public String getKengenCd()
	{
		return cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH);
	}
	public String getKengenCdString()
	{
		return "'" + cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH) + "'";
	}
	public String getKengenCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.kengen_cd,KENGEN_CD_MAX_LENGTH));
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


	// user_naに対するセッターとゲッターの集合
	public boolean setUserNa(String user_na)
	{
		this.user_na = user_na;
		return true;
	}
	public String getUserNa()
	{
		return cutString(this.user_na,USER_NA_MAX_LENGTH);
	}
	public String getUserNaString()
	{
		return "'" + cutString(this.user_na,USER_NA_MAX_LENGTH) + "'";
	}
	public String getUserNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.user_na,USER_NA_MAX_LENGTH));
	}


	// yuko_start_dtに対するセッターとゲッターの集合
	public boolean setYukoStartDt(String yuko_start_dt)
	{
		this.yuko_start_dt = yuko_start_dt;
		return true;
	}
	public String getYukoStartDt()
	{
		return cutString(this.yuko_start_dt,YUKO_START_DT_MAX_LENGTH);
	}
	public String getYukoStartDtString()
	{
		return "'" + cutString(this.yuko_start_dt,YUKO_START_DT_MAX_LENGTH) + "'";
	}
	public String getYukoStartDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_start_dt,YUKO_START_DT_MAX_LENGTH));
	}


	// yuko_end_dtに対するセッターとゲッターの集合
	public boolean setYukoEndDt(String yuko_end_dt)
	{
		this.yuko_end_dt = yuko_end_dt;
		return true;
	}
	public String getYukoEndDt()
	{
		return cutString(this.yuko_end_dt,YUKO_END_DT_MAX_LENGTH);
	}
	public String getYukoEndDtString()
	{
		return "'" + cutString(this.yuko_end_dt,YUKO_END_DT_MAX_LENGTH) + "'";
	}
	public String getYukoEndDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.yuko_end_dt,YUKO_END_DT_MAX_LENGTH));
	}


	// ip_addressに対するセッターとゲッターの集合
	public boolean setIpAddress(String ip_address)
	{
		this.ip_address = ip_address;
		return true;
	}
	public String getIpAddress()
	{
		return cutString(this.ip_address,IP_ADDRESS_MAX_LENGTH);
	}
	public String getIpAddressString()
	{
		return "'" + cutString(this.ip_address,IP_ADDRESS_MAX_LENGTH) + "'";
	}
	public String getIpAddressHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ip_address,IP_ADDRESS_MAX_LENGTH));
	}


	// update_dtに対するセッターとゲッターの集合
	public boolean setUpdateDt(String update_dt)
	{
		this.update_dt = update_dt;
		return true;
	}
	public String getUpdateDt()
	{
		return cutString(this.update_dt,UPDATE_DT_MAX_LENGTH);
	}
	public String getUpdateDtString()
	{
		return "'" + cutString(this.update_dt,UPDATE_DT_MAX_LENGTH) + "'";
	}
	public String getUpdateDtHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.update_dt,UPDATE_DT_MAX_LENGTH));
	}


	/**
	 * 変換後の操作者業種に対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSosasyaGyosyu(String sosasya_gyosyu)
	{
		this.sosasya_gyosyu = sosasya_gyosyu;
		return true;
	}
	/**
	 * 変換後の操作者業種に対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSosasyaGyosyu()
	{
		return this.sosasya_gyosyu;
	}



	/**
	 * 業種選択メニュー.衣料服飾ボタン押下可否フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGyosyuMenuIryoFlg(String gyosyu_menu_iryo_flg)
	{
		this.gyosyu_menu_iryo_flg = gyosyu_menu_iryo_flg;
		return true;
	}
	/**
	 * 業種選択メニュー.衣料服飾ボタン押下可否フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGyosyuMenuIryoFlg()
	{
		return this.gyosyu_menu_iryo_flg;
	}

	/**
	 * 業種選択メニュー.住生活ボタン押下可否フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGyosyuMenuLivFlg(String gyosyu_menu_liv_flg)
	{
		this.gyosyu_menu_liv_flg = gyosyu_menu_liv_flg;
		return true;
	}
	/**
	 * 業種選択メニュー.住生活ボタン押下可否フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGyosyuMenuLivFlg()
	{
		return this.gyosyu_menu_liv_flg;
	}

	/**
	 * 業種選択メニュー.加工食品ボタン押下可否フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGyosyuMenuGroFlg(String gyosyu_menu_gro_flg)
	{
		this.gyosyu_menu_gro_flg = gyosyu_menu_gro_flg;
		return true;
	}
	/**
	 * 業種選択メニュー.加工食品ボタン押下可否フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGyosyuMenuGroFlg()
	{
		return this.gyosyu_menu_gro_flg;
	}




	/**
	 * 業種選択メニュー.生鮮ボタン押下可否フラグに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setGyosyuMenuFreshFlg(String gyosyu_menu_fresh_flg)
	{
		this.gyosyu_menu_fresh_flg = gyosyu_menu_fresh_flg;
		return true;
	}
	/**
	 * 業種選択メニュー.生鮮ボタン押下可否フラグに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getGyosyuMenuFreshFlg()
	{
		return this.gyosyu_menu_fresh_flg;
	}

	/**
	 * 業種選択メニュー画面で選択された業種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setSelectedGyosyuCd(String selected_gyosyu_cd)
	{
		this.selected_gyosyu_cd = selected_gyosyu_cd;
		return true;
	}
	/**
	 * 業種選択メニュー画面で選択された業種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getSelectedGyosyuCd()
	{
		return this.selected_gyosyu_cd;
	}

	/**
	 * 業種選択メニュー画面で選択された業種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setMiKakunin(String mi_kakunin)
	{
		this.mi_kakunin = mi_kakunin;
		return true;
	}
	/**
	 * 業種選択メニュー画面で選択された業種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getMiKakunin()
	{
		return this.mi_kakunin;
	}


	/**
	 * 業種選択メニュー画面で選択された業種コードに対するセッター<br>
	 * <br>
	 * Ex)<br>
	 * setKanjiRn("文字列");<br>
	 * <br>
	 * @param String 設定する文字列
	 */
	public boolean setError(String error)
	{
		this.error = error;
		return true;
	}
	/**
	 * 業種選択メニュー画面で選択された業種コードに対するゲッター<br>
	 * <br>
	 * Ex)<br>
	 * getKanjiRn();　戻り値　文字列<br>
	 * <br>
	 * @return String 文字列
	 */
	public String getError()
	{
		return this.error;
	}



	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  user_id = " + getUserIdString()
			+ "  user_password = " + getUserPasswordString()
			+ "  hojin_cd = " + getHojinCdString()
			+ "  gyosyu_cd = " + getGyosyuCdString()
			+ "  s_gyosyu_cd = " + getSGyosyuCdString()
			+ "  uriku1_cd = " + getUriku1CdString()
			+ "  uriku2_cd = " + getUriku2CdString()
			+ "  uriku3_cd = " + getUriku3CdString()
			+ "  kengen_cd = " + getKengenCdString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  user_na = " + getUserNaString()
			+ "  yuko_start_dt = " + getYukoStartDtString()
			+ "  yuko_end_dt = " + getYukoEndDtString()
			+ "  ip_address = " + getIpAddressString()
			+ "  update_dt = " + getUpdateDtString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return SysSosasyaBean コピー後のクラス
	 */
	public mst000501_SysSosasyaBean createClone()
	{
		mst000501_SysSosasyaBean bean = new mst000501_SysSosasyaBean();
		bean.setUserId(this.user_id);
		bean.setUserPassword(this.user_password);
		bean.setHojinCd(this.hojin_cd);
		bean.setGyosyuCd(this.gyosyu_cd);
		bean.setSGyosyuCd(this.s_gyosyu_cd);
		bean.setUriku1Cd(this.uriku1_cd);
		bean.setUriku2Cd(this.uriku2_cd);
		bean.setUriku3Cd(this.uriku3_cd);
		bean.setKengenCd(this.kengen_cd);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setUserNa(this.user_na);
		bean.setYukoStartDt(this.yuko_start_dt);
		bean.setYukoEndDt(this.yuko_end_dt);
		bean.setIpAddress(this.ip_address);
		bean.setUpdateDt(this.update_dt);
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
		if( !( o instanceof mst000501_SysSosasyaBean ) ) return false;
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
				byte bt[] = base.substring(pos,pos+1).getBytes("UTF-8");
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

	// ↓↓　2006/04/18 kim START
	/** 店舗コード存在フラグに対するゲッター
	 * @return
	 */
	public String getPosTenpoSonZai_fg() {
		return posTenpoSonZai_fg;
	}

	/**	店舗コード存在フラグに対するセッター
	 * @param string
	 */
	public void setPosTenpoSonZai_fg(String string) {
		posTenpoSonZai_fg = string;
	}
	//	↑↑　2006/04/18 kim END
}
