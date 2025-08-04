package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author Y.Inaba Creator(2004.05.31) Version 1.0.IST_CUSTOM.2
 * @version 1.0 (Create time: 2005/5/31 21:25:22)
 */
public class mst110401_AssortmentBean{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int SYOHIN_CD_MAX_LENGTH               = 13;	// 商品コードの長さ
	public static final int COLOR_CD_MAX_LENGTH                = 2;	// カラーコードの長さ
	public static final int SIZE_CD_MAX_LENGTH                 = 2;	// サイズコードの長さ
	public static final int PATTERN_1_QT_MAX_LENGTH            = 4;	// 発注パターン１の長さ
	public static final int PATTERN_2_QT_MAX_LENGTH            = 4;	// 発注パターン２の長さ
	public static final int PATTERN_3_QT_MAX_LENGTH            = 4;	// 発注パターン３の長さ
	public static final int PATTERN_4_QT_MAX_LENGTH            = 4;	// 発注パターン４の長さ
	public static final int PATTERN_5_QT_MAX_LENGTH            = 4;	// 発注パターン５の長さ
	public static final int TAG_HAKO_QT_MAX_LENGTH             = 6;	// タグ発行枚数の長さ
	public static final int TOTAL_TAG_HAKO_QT_MAX_LENGTH       = 6;	// 累計タグ発行枚数の長さ
	public static final int HACHU_TEISI_KB_MAX_LENGTH          = 1;	// 発注停止区分の長さ
	public static final int SAIHAKO_FG_MAX_LENGTH              = 1;	// 再発行フラグの長さ
	public static final int TORIHIKISAKI_RENDOBI_DT_MAX_LENGTH = 8;	// 取引先連動日の長さ
	public static final int INSERT_TS_MAX_LENGTH               = 20;	// 登録年月日の長さ
	public static final int INSERT_USER_ID_MAX_LENGTH          = 10;	// 登録者社員ＩＤの長さ
	public static final int UPDATE_TS_MAX_LENGTH               = 20;	// 更新年月日の長さ
	public static final int UPDATE_USER_ID_MAX_LENGTH          = 10;	// 更新者社員ＩＤの長さ
	public static final int DELETE_FG_MAX_LENGTH               = 1;	// 削除フラグの長さ

	private String syohin_cd               = null;		// 商品コード
	private String color_cd                = null;		// カラーコード
	private String size_cd                 = null;		// サイズコード
	private String pattern_1_qt            = null;		// 発注パターン１
	private String pattern_2_qt            = null;		// 発注パターン２
	private String pattern_3_qt            = null;		// 発注パターン３
	private String pattern_4_qt            = null;		// 発注パターン４
	private String pattern_5_qt            = null;		// 発注パターン５
	private String tag_hako_qt             = null;		// タグ発行枚数
	private String total_tag_hako_qt       = null;		// 累積タグ発行枚数
	private String hachu_teisi_kb = null;				// 発注停止区分
	private String saihako_fg = null;					// 再発行フラグ
	private String torihikisaki_rendobi_dt = null;		// 取引先連動日
	private String insert_ts = null;					// 作成年月日
	private String insert_user_id = null;				// 作成者社員ＩＤ
	private String update_ts = null;					// 更新年月日
	private String update_user_id = null;				// 更新者社員ＩＤ
	private String delete_fg = null;					// 削除フラグ

	// DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase(){
		createDatabase = true;
	}
	public boolean isCreateDatabase(){
		return createDatabase;
	}

	/**
	 * RAssortmentBeanを１件のみ抽出したい時に使用する
	 */
	public static mst110401_AssortmentBean getRAssortmentBean(DataHolder dataHolder){
		mst110401_AssortmentBean bean = null;
		BeanHolder beanHolder = new BeanHolder( new mst110401_AssortmentDM() );
		try{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (mst110401_AssortmentBean )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e){
		}
		return bean;
	}

	// syohin_cdに対するセッターとゲッターの集合
	public boolean setSyohinCd(String syohin_cd){
		this.syohin_cd = syohin_cd;
		return true;
	}
	public String getSyohinCd(){
		return cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH);
	}
	public String getSyohinCdString(){
		return "'" + cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH) + "'";
	}
	public String getSyohinCdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.syohin_cd,SYOHIN_CD_MAX_LENGTH));
	}

	// color_cdに対するセッターとゲッターの集合
	public boolean setColorCd(String color_cd){
		this.color_cd = color_cd;
		return true;
	}
	public String getColorCd(){
		return cutString(this.color_cd,COLOR_CD_MAX_LENGTH);
	}
	public String getColorCdString(){
		return "'" + cutString(this.color_cd,COLOR_CD_MAX_LENGTH) + "'";
	}
	public String getColorCdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.color_cd,COLOR_CD_MAX_LENGTH));
	}

	// size_cdに対するセッターとゲッターの集合
	public boolean setSizeCd(String size_cd){
		this.size_cd = size_cd;
		return true;
	}
	public String getSizeCd(){
		return cutString(this.size_cd,SIZE_CD_MAX_LENGTH);
	}
	public String getSizeCdString(){
		return "'" + cutString(this.size_cd,SIZE_CD_MAX_LENGTH) + "'";
	}
	public String getSizeCdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.size_cd,SIZE_CD_MAX_LENGTH));
	}

	// pattern_1_qtに対するセッターとゲッターの集合
	public boolean setPattern1Qt(String pattern_1_qt){
		this.pattern_1_qt = pattern_1_qt;
		return true;
	}
	public String getPattern1Qt(){
		return cutString(this.pattern_1_qt,PATTERN_1_QT_MAX_LENGTH);
	}
	public String getPattern1QtString(){
		return "'" + cutString(this.pattern_1_qt,PATTERN_1_QT_MAX_LENGTH) + "'";
	}
	public String getPatternQt1HTMLString(){
		return HTMLStringUtil.convert(cutString(this.pattern_1_qt,PATTERN_1_QT_MAX_LENGTH));
	}

	// pattern_2_qtに対するセッターとゲッターの集合
	public boolean setPattern2Qt(String pattern_2_qt){
		this.pattern_2_qt = pattern_2_qt;
		return true;
	}
	public String getPattern2Qt(){
		return cutString(this.pattern_2_qt,PATTERN_2_QT_MAX_LENGTH);
	}
	public String getPattern2QtString(){
		return "'" + cutString(this.pattern_2_qt,PATTERN_2_QT_MAX_LENGTH) + "'";
	}
	public String getPattern2QtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.pattern_2_qt,PATTERN_2_QT_MAX_LENGTH));
	}

	// pattern_3_qtに対するセッターとゲッターの集合
	public boolean setPattern3Qt(String pattern_3_qt){
		this.pattern_3_qt = pattern_3_qt;
		return true;
	}
	public String getPattern3Qt(){
		return cutString(this.pattern_3_qt,PATTERN_3_QT_MAX_LENGTH);
	}
	public String getPattern3QtString(){
		return "'" + cutString(this.pattern_3_qt,PATTERN_3_QT_MAX_LENGTH) + "'";
	}
	public String getPattern3QtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.pattern_3_qt,PATTERN_3_QT_MAX_LENGTH));
	}

	// pattern_4_qtに対するセッターとゲッターの集合
	public boolean setPattern4Qt(String pattern_4_qt){
		this.pattern_4_qt = pattern_4_qt;
		return true;
	}
	public String getPattern4Qt(){
		return cutString(this.pattern_4_qt,PATTERN_4_QT_MAX_LENGTH);
	}
	public String getPattern4QtString(){
		return "'" + cutString(this.pattern_4_qt,PATTERN_4_QT_MAX_LENGTH) + "'";
	}
	public String getPattern4QtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.pattern_4_qt,PATTERN_4_QT_MAX_LENGTH));
	}

	// pattern_5_qtに対するセッターとゲッターの集合
	public boolean setPattern5Qt(String pattern_5_qt){
		this.pattern_5_qt = pattern_5_qt;
		return true;
	}
	public String getPattern5Qt(){
		return cutString(this.pattern_5_qt,PATTERN_5_QT_MAX_LENGTH);
	}
	public String getPattern5QtString(){
		return "'" + cutString(this.pattern_5_qt,PATTERN_5_QT_MAX_LENGTH) + "'";
	}
	public String getPattern5QtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.pattern_5_qt,PATTERN_5_QT_MAX_LENGTH));
	}

	// tag_hako_qtに対するセッターとゲッターの集合
	public boolean setTagHakoQt(String tag_hako_qt){
		this.tag_hako_qt = tag_hako_qt;
		return true;
	}
	public String getTagHakoQt(){
		return cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH);
	}
	public String getTagHakoQtString(){
		return "'" + cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH) + "'";
	}
	public String getTagHakoQtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.tag_hako_qt,TAG_HAKO_QT_MAX_LENGTH));
	}

	// total_tag_hako_qtに対するセッターとゲッターの集合
	public boolean setTotalTagHakoQt(String total_tag_hako_qt){
		this.total_tag_hako_qt = total_tag_hako_qt;
		return true;
	}
	public String getTotalTagHakoQt(){
		return cutString(this.total_tag_hako_qt,TOTAL_TAG_HAKO_QT_MAX_LENGTH);
	}
	public String getTotalTagHakoQtString(){
		return "'" + cutString(this.total_tag_hako_qt,TOTAL_TAG_HAKO_QT_MAX_LENGTH) +"'";
	}
	public String getTotalTagHakoQtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.total_tag_hako_qt,TOTAL_TAG_HAKO_QT_MAX_LENGTH));
	}

	// hachu_teisi_kbに対するセッターとゲッターの集合
	public boolean setHachuTeisiKb(String hachu_teisi_kb){
		this.hachu_teisi_kb = hachu_teisi_kb;
		return true;
	}
	public String getHachuTeisiKb(){
		return cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH);
	}
	public String getHachuTeisiKbString(){
		return "'" + cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH) + "'";
	}
	public String getHachuTeisiKbHTMLString(){
		return HTMLStringUtil.convert(cutString(this.hachu_teisi_kb,HACHU_TEISI_KB_MAX_LENGTH));
	}

	// saihako_fgに対するセッターとゲッターの集合
	public boolean setSaihakoFg(String saihako_fg){
		this.saihako_fg = saihako_fg;
		return true;
	}
	public String getSaihakoFg(){
		return cutString(this.saihako_fg,SAIHAKO_FG_MAX_LENGTH);
	}
	public String getSaihakoFgString(){
		return "'" + cutString(this.saihako_fg,SAIHAKO_FG_MAX_LENGTH) + "'";
	}
	public String getSaihakoFgHTMLString(){
		return HTMLStringUtil.convert(cutString(this.saihako_fg,SAIHAKO_FG_MAX_LENGTH));
	}

	// torihikisaki_rendobi_dtに対するセッターとゲッターの集合
	public boolean setTorihikisakiRendobiDt(String torihikisaki_rendobi_dt){
		this.torihikisaki_rendobi_dt = torihikisaki_rendobi_dt;
		return true;
	}
	public String getTorihikisakiRendobiDt(){
		return cutString(this.torihikisaki_rendobi_dt,TORIHIKISAKI_RENDOBI_DT_MAX_LENGTH);
	}
	public String getTorihikisakiRendobiDtString(){
		return "'" + cutString(this.torihikisaki_rendobi_dt,TORIHIKISAKI_RENDOBI_DT_MAX_LENGTH) + "'";
	}
	public String getTorihikisakiRendobiDtHTMLString(){
		return HTMLStringUtil.convert(cutString(this.torihikisaki_rendobi_dt,TORIHIKISAKI_RENDOBI_DT_MAX_LENGTH));
	}

	// insert_tsに対するセッターとゲッターの集合
	public boolean setInsertTs(String insert_ts){
		this.insert_ts = insert_ts;
		return true;
	}
	public String getInsertTs(){
		return cutString(this.insert_ts,INSERT_TS_MAX_LENGTH);
	}
	public String getInsertTsString(){
		return "'" + cutString(this.insert_ts,INSERT_TS_MAX_LENGTH) + "'";
	}
	public String getInsertTsHTMLString(){
		return HTMLStringUtil.convert(cutString(this.insert_ts,INSERT_TS_MAX_LENGTH));
	}

	// insert_user_idに対するセッターとゲッターの集合
	public boolean setInsertUserId(String insert_user_id){
		this.insert_user_id = insert_user_id;
		return true;
	}
	public String getInsertUserId(){
		return cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH);
	}
	public String getInsertUserIdString(){
		return "'" + cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH) + "'";
	}
	public String getInsertUserIdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.insert_user_id,INSERT_USER_ID_MAX_LENGTH));
	}

	// update_tsに対するセッターとゲッターの集合
	public boolean setUpdateTs(String update_ts){
		this.update_ts = update_ts;
		return true;
	}
	public String getUpdateTs(){
		return cutString(this.update_ts,UPDATE_TS_MAX_LENGTH);
	}
	public String getUpdateTsString(){
		return "'" + cutString(this.update_ts,UPDATE_TS_MAX_LENGTH) + "'";
	}
	public String getUpdateTsHTMLString(){
		return HTMLStringUtil.convert(cutString(this.update_ts,UPDATE_TS_MAX_LENGTH));
	}

	// update_user_idに対するセッターとゲッターの集合
	public boolean setUpdateUserId(String update_user_id){
		this.update_user_id = update_user_id;
		return true;
	}
	public String getUpdateUserId(){
		return cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH);
	}
	public String getUpdateUserIdString(){
		return "'" + cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH) + "'";
	}
	public String getUpdateUserIdHTMLString(){
		return HTMLStringUtil.convert(cutString(this.update_user_id,UPDATE_USER_ID_MAX_LENGTH));
	}

	// delete_fgに対するセッターとゲッターの集合
	public boolean setDeleteFg(String delete_fg){
		this.delete_fg = delete_fg;
		return true;
	}
	public String getDeleteFg(){
		return cutString(this.delete_fg,DELETE_FG_MAX_LENGTH);
	}
	public String getDeleteFgString(){
		return "'" + cutString(this.delete_fg,DELETE_FG_MAX_LENGTH) + "'";
	}
	public String getDeleteFgHTMLString(){
		return HTMLStringUtil.convert(cutString(this.delete_fg,DELETE_FG_MAX_LENGTH));
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString(){
		return "  syohin_cd = " + getSyohinCdString()
			+ "  color_cd = " + getColorCdString()
			+ "  size_cd = " + getSizeCdString()
			+ "  pattern_1_qt = " + getPattern1QtString()
			+ "  pattern_2_qt = " + getPattern2QtString()
			+ "  pattern_3_qt = " + getPattern3QtString()
			+ "  pattern_4_qt = " + getPattern4QtString()
			+ "  pattern_5_qt = " + getPattern5QtString()
			+ "  tag_hako_qt = " + getTagHakoQtString()
			+ "  total_tag_hako_qt = " + getTotalTagHakoQtString()
			+ "  hachu_teisi_kb = " + getHachuTeisiKbString()
			+ "  saihako_fg = " + getSaihakoFgString()
			+ "  torihikisaki_rendobi_dt = " + getTorihikisakiRendobiDtString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  delete_fg = " + getDeleteFgString()
			+ " createDatabase  = " + createDatabase;
	}

	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return RAssortmentBean コピー後のクラス
	 */
	public mst110401_AssortmentBean createClone(){
		mst110401_AssortmentBean bean = new mst110401_AssortmentBean();
		bean.setSyohinCd(this.syohin_cd);
		bean.setColorCd(this.color_cd);
		bean.setSizeCd(this.size_cd);
		bean.setPattern1Qt(this.pattern_1_qt);
		bean.setPattern2Qt(this.pattern_2_qt);
		bean.setPattern3Qt(this.pattern_3_qt);
		bean.setPattern4Qt(this.pattern_4_qt);
		bean.setPattern5Qt(this.pattern_5_qt);
		bean.setTagHakoQt(this.tag_hako_qt);
		bean.setTotalTagHakoQt(this.total_tag_hako_qt);
		bean.setHachuTeisiKb(this.hachu_teisi_kb);
		bean.setSaihakoFg(this.saihako_fg);
		bean.setTorihikisakiRendobiDt(this.torihikisaki_rendobi_dt);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setDeleteFg(this.delete_fg);
		if( this.isCreateDatabase() ) bean.setCreateDatabase();
		return bean;
	}

	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o ){
		if( o == null ) return false;
		if( !( o instanceof mst110401_AssortmentBean ) ) return false;
		return this.toString().equals( o.toString() );
	}

	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max ){
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
