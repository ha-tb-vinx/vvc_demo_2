/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820201_ThemeItem用テーマ別アイテム一覧の表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820201_ThemeItem用テーマ別アイテム一覧の表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
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

/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst820201_ThemeItem用テーマ別アイテム一覧の表示用レコード格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst820201_ThemeItem用テーマ別アイテム一覧の表示用レコード格納用クラス(DmCreatorにより自動生成)</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius S.Takahashi
 * @version 1.0
 * @see なし
 * 変更履歴：2006-03-29 M.Kawamoto
 * 　　　　　1.計量単位の取得がなされていなかったため追加
 */
public class mst820201_ThemeItemListBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SYOHIN_YOBIDASI_MAX_LENGTH	= 4;	//呼出NOの長さ
	public static final int HINSYU_CD_MAX_LENGTH 		= 4;	//品種の長さ
	public static final int SYOHIN_CD_MAX_LENGTH 		= 13;	//商品コードの長さ
	public static final int KEIRYOKI_NA_MAX_LENGTH 		= 50;	//品名の長さ
	public static final int BACK_LAVEL_KB_MAX_LENGTH    	= 1;	//添加物区分の長さ
	public static final int HOZON_ONDOTAI_KB_MAX_LENGTH	= 2;	//保存区分の長さ
	public static final int COMMENT_KB_MAX_LENGTH		= 2;	//コメント区分の長さ
	public static final int BIKO_TX_MAX_LENGTH			= 100;	//備考の長さ
	public static final int TEIGAKU_VL_MAX_LENGTH		= 6;	//定額内容量の長さ
	public static final int TEIGAKUJI_TANI_KB_MAX_LENGTH	= 2;	//定額時単位の長さ
	public static final int TEIGAKU_UP_KB_MAX_LENGTH = 1;//定額・UP区分の長さ
	public static final int TANKA_VL_MAX_LENGTH			= 10;	//単価の長さ
	public static final int UPDATE_TS_MAX_LENGTH			= 20;	//更新日の長さ
	public static final int SANTI_KB_MAX_LENGTH = 3;				// 産地区分の長さ
	public static final int CHORIYO_KOKOKUBUN_KB_MAX_LENGTH = 3;	// 調理用広告文の長さ
	//2006-03-29 M.Kawamoto 計量単位追加
	public static final int KEIRYO_UNIT_NAME_MAX_LENGTH = 2;	// 計量単位の長さ

	private String syohin_yobidasi		= null;	//呼出NO
	private String hinsyu_cd			= null;	//品種コード
	private String syohin_cd			= null;	//商品コード
	private String keiryoki_na			= null;	//品名
	private String back_label_kb		= null;	//添加物区分
	private long syomikikan_vl		= 0;		//賞味期限
	private String hozon_ondotai_kb	= null;	//保存区分
	private String comment_kb			= null;	//コメント区分
	private String biko_tx				= null;	//備考
	private String teigaku_vl			= null;	//定額内容量
	private String teigakuji_tani_kb	= null;	//定額時単位
	private String teigaku_up_kb = null;			//定額・UP区分
	private String tanka_vl			= null;	//単価
	private String update_ts			= null;	//更新日
	private String santi_kb = null;				//産地番号
	private String choriyo_kokokubun_kb = null;	//調理用広告文
	
	private String tenkabutu_nm = null;	//添加物名称
	private String syomikikan_kb = null;	//賞味期間計算区分
	//2006-03-29 M.Kawamoto 計量単位追加
	private String keiryounitname = null; //計量器単位
	

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
	 * mst820201_ThemeItemListBeanを１件のみ抽出したい時に使用する
	 */
	public static mst820201_ThemeItemListBean getMst820201_ThemeItemListBean(DataHolder dataHolder)
	{
		mst820201_ThemeItemListBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst820201_ThemeItemListDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst820201_ThemeItemListBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}


	//添加物名称に対するセッターとゲッターの集合
	public boolean setTenkabutuNm(String tenkabutu_nm) {
		
		this.tenkabutu_nm = tenkabutu_nm;
		return true;
	}
	public String getTenkabutuNm() {
		
		return this.tenkabutu_nm;
	}
	
	//賞味期間計算区分に対するセッターとゲッターの集合
	public boolean setSyomikikanKb(String syomikikan_kb) {
		
		this.syomikikan_kb = syomikikan_kb;
		return true;
	}
	public String getSyomikikanKb() {
		
		return this.syomikikan_kb;
	}


	// syohin_yobidasiに対するセッターとゲッターの集合
	public boolean setSyohinYobidasi(String syohin_yobidasi)
	{
		this.syohin_yobidasi = syohin_yobidasi;
		return true;
	}
	public String getSyohinYobidasi()
	{
		return cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH);
	}
	public String getSyohinYobidasiString()
	{
		return "'" + cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH) + "'";
	}
	public String getSyohinYobidasiHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.syohin_yobidasi,SYOHIN_YOBIDASI_MAX_LENGTH));
	}


	// hinsyu_cdに対するセッターとゲッターの集合
	public boolean setHinsyuCd(String hinsyu_cd)
	{
		this.hinsyu_cd = hinsyu_cd;
		return true;
	}
	public String getHinsyuCd()
	{
		return cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH);
	}
	public String getHinsyuCdString()
	{
		return "'" + cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH) + "'";
	}
	public String getHinsyuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hinsyu_cd,HINSYU_CD_MAX_LENGTH));
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
	
	
	// keiryoki_naに対するセッターとゲッターの集合
	public boolean setKeiryokiNa(String keiryoki_na)
	{
		this.keiryoki_na = keiryoki_na;
		return true;
	}
	public String getKeiryokiNa()
	{
		return cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH);
	}
	public String getKeiryokiNaString()
	{
		return "'" + cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH) + "'";
	}
	public String getKeiryokiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryoki_na,KEIRYOKI_NA_MAX_LENGTH));
	}
	
	
	// back_label_kbに対するセッターとゲッターの集合
	public boolean setBackLabelKb(String back_label_kb)
	{
		this.back_label_kb = back_label_kb;
		return true;
	}
	public String getBackLabelKb()
	{
		return cutString(this.back_label_kb,BACK_LAVEL_KB_MAX_LENGTH);
	}
	public String getBackLabelKbString()
	{
		return "'" + cutString(this.back_label_kb,BACK_LAVEL_KB_MAX_LENGTH) + "'";
	}
	public String getBackLabelKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.back_label_kb,BACK_LAVEL_KB_MAX_LENGTH));
	}
	
	
	// syomikikan_vlに対するセッターとゲッターの集合
	public boolean setSyomikikanVl(String syomikikan_vl)
	{
		try
		{
			this.syomikikan_vl = Long.parseLong(syomikikan_vl);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setSyomikikanVl(long syomikikan_vl)
	{
		this.syomikikan_vl = syomikikan_vl;
		return true;
	}
	public long getSyomikikanVl()
	{
		return this.syomikikan_vl;
	}
	public String getSyomikikanVlString()
	{
		return Long.toString(this.syomikikan_vl);
	}


	// hozon_ondotai_kbに対するセッターとゲッターの集合
	public boolean setHozonOndotaiKb(String hozon_ondotai_kb)
	{
		this.hozon_ondotai_kb = hozon_ondotai_kb;
		return true;
	}
	public String getHozonOndotaiKb()
	{
		return cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH);
	}
	public String getHozonOndotaiKbString()
	{
		return "'" + cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH) + "'";
	}
	public String getHozonOndotaiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.hozon_ondotai_kb,HOZON_ONDOTAI_KB_MAX_LENGTH));
	}

	// comment_kbに対するセッターとゲッターの集合
	public boolean setCommentKb(String comment_kb)
	{
		this.comment_kb = comment_kb;
		return true;
	}
	public String getCommentKb()
	{
		return cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH);
	}
	public String getCommentKbString()
	{
		return "'" + cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH) + "'";
	}
	public String getCommentKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.comment_kb,COMMENT_KB_MAX_LENGTH));
	}

	// biko_txに対するセッターとゲッターの集合
	public boolean setBikoTx(String biko_tx)
	{
		this.biko_tx = biko_tx;
		return true;
	}
	public String getBikoTx()
	{
		return cutString(this.biko_tx,BIKO_TX_MAX_LENGTH);
	}
	public String getBikoTxString()
	{
		return "'" + cutString(this.biko_tx,BIKO_TX_MAX_LENGTH) + "'";
	}
	public String getBikoTxHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.biko_tx,BIKO_TX_MAX_LENGTH));
	}

	// teigaku_vlに対するセッターとゲッターの集合
	public boolean setTeigakuVl(String teigaku_vl)
	{
		this.teigaku_vl = teigaku_vl;
		return true;
	}
	public String getTeigakuVl()
	{
		return cutString(this.teigaku_vl,TEIGAKU_VL_MAX_LENGTH);
	}
	public String getTeigakuVlString()
	{
		return "'" + cutString(this.teigaku_vl,TEIGAKU_VL_MAX_LENGTH) + "'";
	}
	public String getTeigakuVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigaku_vl,TEIGAKU_VL_MAX_LENGTH));
	}

	// teigakuji_tani_kbに対するセッターとゲッターの集合
	public boolean setTeigakujiTaniKb(String teigakuji_tani_kb)
	{
		this.teigakuji_tani_kb = teigakuji_tani_kb;
		return true;
	}
	public String getTeigakujiTaniKb()
	{
		return cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH);
	}
	public String getTeigakujiTaniKbString()
	{
		return "'" + cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakujiTaniKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigakuji_tani_kb,TEIGAKUJI_TANI_KB_MAX_LENGTH));
	}


	// teigaku_up_kbに対するセッターとゲッターの集合
	public boolean setTeigakuUpKb(String teigaku_up_kb)
	{
		this.teigaku_up_kb = teigaku_up_kb;
		return true;
	}
	public String getTeigakuUpKb()
	{
		return cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH);
	}
	public String getTeigakuUpKbString()
	{
		return "'" + cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH) + "'";
	}
	public String getTeigakuUpKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.teigaku_up_kb,TEIGAKU_UP_KB_MAX_LENGTH));
	}

	// tanka_vlに対するセッターとゲッターの集合
	public boolean setTankaVl(String tanka_vl)
	{
		this.tanka_vl = tanka_vl;
		return true;
	}
	public String getTankaVl()
	{
		return cutString(this.tanka_vl,TANKA_VL_MAX_LENGTH);
	}
	public String getTankaVlString()
	{
		return "'" + cutString(this.tanka_vl,TANKA_VL_MAX_LENGTH) + "'";
	}
	public String getTankaVlHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tanka_vl,TANKA_VL_MAX_LENGTH));
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

	
	// santi_kbに対するセッターとゲッターの集合
	public boolean setSantiKb(String santi_kb)
	{
		this.santi_kb = santi_kb;
		return true;
	}
	public String getSantiKb()
	{
		return cutString(this.santi_kb,SANTI_KB_MAX_LENGTH);
	}
	public String getSantiKbString()
	{
		return "'" + cutString(this.santi_kb,SANTI_KB_MAX_LENGTH) + "'";
	}
	public String getSantiKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.santi_kb,SANTI_KB_MAX_LENGTH));
	}


	// choriyo_kokokubun_kbに対するセッターとゲッターの集合
	public boolean setChoriyoKokokubunKb(String choriyo_kokokubun_kb)
	{
		this.choriyo_kokokubun_kb = choriyo_kokokubun_kb;
		return true;
	}
	public String getChoriyoKokokubunKb()
	{
		return cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH);
	}
	public String getChoriyoKokokubunKbString()
	{
		return "'" + cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH) + "'";
	}
	public String getChoriyoKokokubunKbHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.choriyo_kokokubun_kb,CHORIYO_KOKOKUBUN_KB_MAX_LENGTH));
	}
	
	//2006-03-29 M.Kawamoto 計量単位追加
	public boolean setKeiRyoUnitName(String keiryounitname)
	{
		this.keiryounitname = keiryounitname;
		return true;
	}
	public String getKeiRyoUnitName()
	{
		return cutString(this.keiryounitname,KEIRYO_UNIT_NAME_MAX_LENGTH);
	}
	public String getKeiRyoUnitNameString()
	{
		return "'" + cutString(this.keiryounitname,KEIRYO_UNIT_NAME_MAX_LENGTH) + "'";
	}
	public String getKeiRyoUnitNameHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.keiryounitname,KEIRYO_UNIT_NAME_MAX_LENGTH));
	}
	

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  syohin_yobidasi = " + getSyohinYobidasiString()
		+ "  hinsyu_cd = " + getHinsyuCdString()
		+ "  syohin_cd = " + getSyohinCdString()
		+ "  keiryoki_na = " + getKeiryokiNaString()
		+ "  back_label_kb = " + getBackLabelKbString()
		+ "  syomikikan_vl = " + getSyomikikanVlString()
		+ "  hozon_ondotai_kb = " + getHozonOndotaiKbString()
		+ "  comment_kb = " + getCommentKbString()
		+ "  biko_tx = " + getBikoTxString()
		+ "  teigaku_up_kb = " + getTeigakuUpKbString()
		+ "  teigaku_vl = " + getTeigakuVlString()
		+ "  teigakuji_tani_kb = " + getTeigakujiTaniKbString()
		+ "  tanka_vl = " + getTankaVlString()
		+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RTenkabutuLBean コピー後のクラス
	 */
	public mst820201_ThemeItemListBean createClone()
	{
		mst820201_ThemeItemListBean bean = new mst820201_ThemeItemListBean();
		bean.setSyohinYobidasi(this.syohin_yobidasi);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setSyohinCd(this.syohin_cd);
		bean.setKeiryokiNa(this.keiryoki_na);
		bean.setBackLabelKb(this.back_label_kb);
		bean.setSyomikikanVl(this.syomikikan_vl);
		bean.setHozonOndotaiKb(this.hozon_ondotai_kb);
		bean.setCommentKb(this.comment_kb);
		bean.setBikoTx(this.biko_tx);
		bean.setTeigakuVl(this.teigaku_vl);
		bean.setTeigakujiTaniKb(this.teigakuji_tani_kb);
		bean.setTeigakuUpKb(this.teigaku_up_kb);
		bean.setTankaVl(this.tanka_vl);
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
		if( !( o instanceof mst840201_TenkabutuListBean ) ) return false;
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
