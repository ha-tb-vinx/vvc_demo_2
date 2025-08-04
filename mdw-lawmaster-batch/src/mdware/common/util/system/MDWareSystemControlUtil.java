package mdware.common.util.system;

import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.bean.SystemControlBean;
import mdware.portal.bean.SeisenSystemControlBean;

/**
 * <p>タイトル: MDWareSystemControlUtil</p>
 * <p>説明: システム管理マスタから必要な情報を引き出す</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author k.nakazawa
 * @version 1.0
 */

public class MDWareSystemControlUtil {

	/**
	 * オンライン年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String オンライン年月日
	 */
	public static String getOnlineDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		//SeisenSystemControlBean seisenSystemControlBean = SeisenSystemControlBean.getSeisenSystemControlBean(dh);
		return( systemControlBean.getOnlineDt() );
	}

	/**
	 * バッチ年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String バッチ年月日
	 */
	public static String getBatchDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getBatchDt() );
	}
	
	/**
	 * マスタオンライン年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String オンライン年月日
	 */
	public static String getMstOnlineDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getMstDateDt() );
	}

	/**
	 * マスタバッチ年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String バッチ年月日
	 */
	public static String getMstBatchDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getBatDateDt() );
	}
	
	/**
	 * 仮発注開始年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String 仮発注開始年月日
	 */
	public static String getPreOrderBeginDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getPreOrderBeginDt() );
	}

	/**
	 * 仮発注終了年月日を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String 仮発注終了年月日
	 */
	public static String getPreOrderEndDate()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getPreOrderEndDt() );
	}

	/**
	 * 仮発注スライド曜日区分を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String 仮発注スライド曜日区分
	 */
	public static String getPreOrderSlideYobiKb()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getPreOrderSlideYobiKb() );
	}

	/**
	 * 仮発注締曜日区分を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String 仮発注締曜日区分
	 */
	public static String getPreOrderSimeKbYobiKb()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getPreOrderSimeKbYobiKb() );
	}

	/**
	 * 最大発注数を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return long 最大発注数
	 */
	public static double getMaxHachuDay()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getMaxHachuDy() );
	}
	
	/**
	 * EOB使用可能フラグを取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String EOB使用可能区分
	 */
	public static String getEobUseFg()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getEobUseFg() );
	}
	
	/**
	 * システム使用可能フラグを取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String EOB使用可能区分
	 */
	public static String getMstUseFg()
	{
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return( systemControlBean.getMstUseFg() );
		//return ( systemControlBean.getEobUseFg() );
	}
	
	public static SystemControlBean getSystemControlBean() {
		DataHolder dh = new DataHolder();
		SystemControlBean systemControlBean = SystemControlBean.getSystemControlBean(dh);
		return systemControlBean;
	}

}