package mdware.master.common.bean;

import java.util.*;

import mdware.portal.bean.DtAlarmDM;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author Bean Creator(2004.11.25) Version 1.1.rbsite
 * @version X.X (Create time: 2006/6/6 17:21:50)
 * @version 3.0 O.Uemura ランドローム様対応
 */
public class MSTB907010_DtAlarmBeanForSyohinMasterHenkoList
extends mdware.portal.bean.DtAlarmBean{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int ALARM_ID_MAX_LENGTH = 20;
	public static final int ALARM_TYPE_NA_MAX_LENGTH = 32;
	public static final int CONTENT_TX_MAX_LENGTH = 256;
	public static final int YUKO_SYURYO_DT_MAX_LENGTH = 8;
	public static final int ALARM_KB_MAX_LENGTH = 1;
	public static final int DESTINATION_USER_ID_MAX_LENGTH = 20;
	public static final int DESTINATION_BUMON_CD_MAX_LENGTH = 4;
	public static final int URL_TX_MAX_LENGTH = 256;
	public static final int PARAMETER_TX_MAX_LENGTH = 256;
	public static final int DEL_KEY_TX_MAX_LENGTH = 40;
	public static final int DEL_FG_MAX_LENGTH = 1;
	public static final int INSERT_TS_MAX_LENGTH = 14;
	public static final int INSERT_USER_ID_MAX_LENGTH = 20;
	public static final int UPDATE_TS_MAX_LENGTH = 14;
	public static final int UPDATE_USER_ID_MAX_LENGTH = 20;
	public static final int COLOR_TX_MAX_LENGTH = 16;
	public static final int DESTINATION_BUSHO_CD_MAX_LENGTH = 4;
	//public static final int DESTINATION_TENPO_CD_MAX_LENGTH = 4;
	public static final int DESTINATION_TENPO_CD_MAX_LENGTH = 6;

	private long alarm_nb = 0;
	private String alarm_id = null;
	private String alarm_type_na = null;
	private String content_tx = null;
	private String yuko_syuryo_dt = null;
	private String alarm_kb = null;
	private String destination_user_id = null;
	private String destination_bumon_cd = null;
	private String url_tx = null;
	private String parameter_tx = null;
	private String del_key_tx = null;
	private String del_fg = null;
	private String insert_ts = null;
	private String insert_user_id = null;
	private String update_ts = null;
	private String update_user_id = null;
	private String color_tx = null;
	private String destinationBusyoCd = null;
	private String destinationTenpoCd = null;


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
	 * DtAlarmBeanを１件のみ抽出したい時に使用する
	 */
	public static MSTB907010_DtAlarmBeanForSyohinMasterHenkoList getDtAlarmBean(DataHolder dataHolder)
	{
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = null;
		BeanHolder beanHolder = new BeanHolder( new DtAlarmDM() );
		try
		{
			Iterator ite = beanHolder.getPageBeanListFromDataHolder( dataHolder ).iterator();
			//１件以上存在する時はまず保存する
			if( ite.hasNext() )
				bean = (MSTB907010_DtAlarmBeanForSyohinMasterHenkoList )ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if( ite.hasNext() )
				bean = null;
		}
		catch(Exception e)
		{
		}
		return bean;
	}



	/**
	 * @return destinationBusyoCd
	 */
	public String getDestinationBusyoCd() {
		return cutString(this.destinationBusyoCd, DESTINATION_BUSHO_CD_MAX_LENGTH);
	}
	/**
	 * @param destinationBusyoCd 設定する destinationBusyoCd
	 */
	public void setDestinationBusyoCd(String destinationBusyoCd) {
		this.destinationBusyoCd = destinationBusyoCd;
	}
	public String getDestinationBusyoCdString()
	{
		return "'" + cutString(this.destinationBusyoCd,DESTINATION_BUSHO_CD_MAX_LENGTH) + "'";
	}
	public String getDestinationBusyoCdTX()
	{
		return cutString(this.destinationBusyoCd,DESTINATION_BUSHO_CD_MAX_LENGTH);
	}


	/**
	 * @return destinationTenpoCd
	 */
	public String getDestinationTenpoCd() {
		return cutString(this.destinationTenpoCd, DESTINATION_TENPO_CD_MAX_LENGTH);
	}
	/**
	 * @param destinationTenpoCd 設定する destinationTenpoCd
	 */
	public void setDestinationTenpoCd(String destinationTenpoCd) {
		this.destinationTenpoCd = destinationTenpoCd;
	}
	public String getDestinationTenpoCdString()
	{
		return "'" + cutString(this.destinationTenpoCd,DESTINATION_TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getDestinationTenpoCdTX()
	{
		return cutString(this.destinationTenpoCd,DESTINATION_TENPO_CD_MAX_LENGTH);
	}

	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  alarm_nb = " + getAlarmNbString()
			+ "  alarm_id = " + getAlarmIdString()
			+ "  alarm_type_na = " + getAlarmTypeNaString()
			+ "  content_tx = " + getContentTxString()
			+ "  yuko_syuryo_dt = " + getYukoSyuryoDtString()
			+ "  alarm_kb = " + getAlarmKbString()
			+ "  destination_user_id = " + getDestinationUserIdString()
			+ "  destination_bumon_cd = " + getDestinationBumonCdString()
			+ "  url_tx = " + getUrlTxString()
			+ "  parameter_tx = " + getParameterTxString()
			+ "  del_key_tx = " + getDelKeyTxString()
			+ "  del_fg = " + getDelFgString()
			+ "  insert_ts = " + getInsertTsString()
			+ "  insert_user_id = " + getInsertUserIdString()
			+ "  update_ts = " + getUpdateTsString()
			+ "  update_user_id = " + getUpdateUserIdString()
			+ "  color_tx = " + getColorTXString()
			+ "  destination_busyo_cd = " + getDestinationBusyoCdString()
			+ "  destination_tenpo_cd = " + getDestinationTenpoCdString()
			+ " createDatabase  = " + createDatabase;
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return DtAlarmBean コピー後のクラス
	 */
	public MSTB907010_DtAlarmBeanForSyohinMasterHenkoList createClone()
	{
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList bean = new MSTB907010_DtAlarmBeanForSyohinMasterHenkoList();
		bean.setAlarmNb(this.alarm_nb);
		bean.setAlarmId(this.alarm_id);
		bean.setAlarmTypeNa(this.alarm_type_na);
		bean.setContentTx(this.content_tx);
		bean.setYukoSyuryoDt(this.yuko_syuryo_dt);
		bean.setAlarmKb(this.alarm_kb);
		bean.setDestinationUserId(this.destination_user_id);
		bean.setDestinationBumonCd(this.destination_bumon_cd);
		bean.setUrlTx(this.url_tx);
		bean.setParameterTx(this.parameter_tx);
		bean.setDelKeyTx(this.del_key_tx);
		bean.setDelFg(this.del_fg);
		bean.setInsertTs(this.insert_ts);
		bean.setInsertUserId(this.insert_user_id);
		bean.setUpdateTs(this.update_ts);
		bean.setUpdateUserId(this.update_user_id);
		bean.setColorTX(this.color_tx);
		bean.setDestinationBusyoCd(this.destinationBusyoCd);
		bean.setDestinationTenpoCd(this.destinationTenpoCd);
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
		if( !( o instanceof MSTB907010_DtAlarmBeanForSyohinMasterHenkoList ) ) return false;
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
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
