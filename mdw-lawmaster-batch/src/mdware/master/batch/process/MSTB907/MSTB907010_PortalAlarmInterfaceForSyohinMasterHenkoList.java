package mdware.master.batch.process.MSTB907;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.batch.log.BatchLog;
import mdware.common.stc.util.db.PreparedStatementDataBase;
import mdware.common.util.date.AbstractMDWareDateGetter;
import mdware.common.util.date.MDWareDateUtility;
import mdware.common.util.db.MDWareSeq;
import mdware.common.util.system.MDWareSystemControlUtil;
import mdware.master.util.RMSTDATEUtil;
import mdware.master.common.bean.MSTB907010_DtAlarmBeanForSyohinMasterHenkoList;
import mdware.master.common.bean.MSTB907010_DtAlarmDMForSyohinMasterHenkoList;
import mdware.portal.bean.MaAlarmBean;
import mdware.portal.bean.MaAlarmDM;
import mdware.portal.dictionary.AlarmProcessKbDic;
import mdware.portal.dictionary.DtAlarmDelFgDic;
import mdware.portal.value.PortalAlarmFinalValue;


/**
 * <P>タイトル : ポータルアラームインターフェース</P>
 * <P>説明 : アラームテーブルへデータの追加・削除を行うメソッド群。各アプリで呼出。</P>
 * <P>著作権: Copyright (c) 2006</p>					
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author VJC K.Nakazawa
 * @version 1.0 (2006.05.16) 初版作成
 * @version 3.0 (2014.01.15) ランドローム様対応 宛先店舗コードを追加
 * @see なし
 */

public class MSTB907010_PortalAlarmInterfaceForSyohinMasterHenkoList extends mdware.portal.process.PortalAlarmInterface {
	
	private static final String logHeader = "PXX-XX-XX アラームＩＦ処理:";	
	//20061102 機能拡張 START
	/**
	 * <p>NULLなら空白を返しNULLでなければtrimして返す</p>
	 * @param	String
	 * @return String
	 */
	private String nullToString( String str )
	{
		if( str == null ) return "";
		return str.trim();
	}
	

	/**
	 * <p>引数のalarm_idをキーとしてMA_ALARMからデータを取得するSQLを取得する</p>
	 * @param	String
	 * @return String
	 */
	private String getMaAlarmSQL( String alarmId )
	{
		if( alarmId == null || alarmId.trim().equals("") )
		{
			return null;
		}
		else 
		{
			alarmId = alarmId.trim();
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT ");
		sb.append(" 	* ");
		sb.append(" FROM ");
		sb.append(" 	MA_ALARM ");
		sb.append(" WHERE ");
		sb.append(" 	ALARM_ID = '" + alarmId + "' ");
	
		return sb.toString();
	}
	
	/**
	 * <p>１行用のINSERTSQLを取得する</p>
	 * @param	DtAlarmBean
	 * @return String
	 */
	private String getInsertSQL( MSTB907010_DtAlarmBeanForSyohinMasterHenkoList da )
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO ");
		sb.append(" 	DT_ALARM ");
		sb.append(" VALUES ( ");
		sb.append(" " + da.getAlarmNb() + ",");				//アラーム番号
		sb.append("'" + da.getAlarmId() + "',");			//アラームＩＤ
		sb.append("'" + da.getAlarmTypeNa() + "',");		//アラーム種別番号
		sb.append("'" + da.getContentTx() + "',");			//内容
		sb.append("'" + da.getYukoSyuryoDt() + "',");		//有効終了日
		sb.append("'" + da.getAlarmKb() + "',");			//アラーム区分
		sb.append("'" + da.getDestinationUserId() + "',");	//宛先ユーザＩＤ
		sb.append("'" + da.getDestinationBumonCd() + "',");	//宛先部門コード
		sb.append("'" + da.getUrlTx() + "',");				//ＵＲＬ
		sb.append("'" + da.getParameterTx() + "',");		//パラメータ
		sb.append("'" + da.getDelKeyTx() + "',");			//削除キー
		sb.append("'" + da.getDelFg() + "',");				//削除フラグ
		sb.append("'" + da.getInsertTs() + "',");			//作成年月日
		sb.append("'" + da.getInsertUserId() + "',");		//作成ユーザＩＤ
		sb.append("'" + da.getUpdateTs() + "',");			//更新年月日
		sb.append("'" + da.getUpdateUserId() + "',");		//更新ユーザＩＤ
		sb.append("'" + da.getColorTX() + "'");				//表示色
		sb.append("'" + da.getDestinationBusyoCdTX() + "'"); //宛先部署コード
		sb.append("'" + da.getDestinationTenpoCdTX() + "'"); //宛先店舗コード
		sb.append(" ) ");
		
		return sb.toString();
	}
	
	/**
	 * <p>PreparedStatement版のINSERTSQLを取得する</p>
	 * @return String
	 */
	private String getInsertSQLPrepared()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO ");
		sb.append(" 	DT_ALARM ");
		sb.append(" VALUES ( ");
		sb.append("?,");//アラーム番号
		sb.append("?,");//アラームＩＤ
		sb.append("?,");//アラーム種別番号
		sb.append("?,");//内容
		sb.append("?,");//有効終了日
		sb.append("?,");//アラーム区分
		sb.append("?,");//宛先ユーザＩＤ
		sb.append("?,");//宛先部門コード
		sb.append("?,");//ＵＲＬ
		sb.append("?,");//パラメータ
		sb.append("?,");//削除キー
		sb.append("?,");//削除フラグ
		sb.append("?,");//作成年月日
		sb.append("?,");//作成ユーザＩＤ
		sb.append("?,");//更新年月日
		sb.append("?,");//更新ユーザＩＤ
		sb.append("?,");//表示色
		sb.append("?,");//宛先部署コード
		sb.append("?");	//宛先店舗コード
		sb.append(" ) ");
		
		return sb.toString();
	}
	//20061102 END
	
	/**
	 * <p>引数の情報からアラームを登録する</p>
	 * @return boolean
	 */
	public MSTB907010_DtAlarmBeanForSyohinMasterHenkoList setAlarmMessage( MSTB907010_DtAlarmBeanForSyohinMasterHenkoList daBean, String processKb ) {
		
		//開始ログ
		if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
			BatchLog.getInstance().getLog().info( "アラームの新規作成処理を開始します" );
		} else {
			StcLog.getInstance().getLog().info( "アラームの新規作成処理を開始します" );
		}
		
		MSTB907010_DtAlarmDMForSyohinMasterHenkoList daDM		= new MSTB907010_DtAlarmDMForSyohinMasterHenkoList();			//新規作成用DM
		MaAlarmDM maDM 		= new MaAlarmDM();			//検索用DM
		MaAlarmBean maBean 	= new MaAlarmBean();		//検索用Bean
		DataHolder dh		= new DataHolder();		//検索用DataHolder
		BeanHolder bh 		= new BeanHolder( maDM );	//新規作成用BeanHolder
		List li 			= new ArrayList();			//新規作成リスト
		try {
			
			//alarm_idの存在チェックを行う(存在しない場合その他扱い)
			if( daBean.getAlarmId() == null || daBean.getAlarmId().trim().equals("") ) {
				//nullか空文字の場合無条件でその他項目になる
				daBean.setAlarmId( DB_ALARM_ID_OTHERS );
			} else {
				dh.updateParameterValue( "alarm_id", daBean.getAlarmId() );
				bh.setDataHolder( dh );
				bh.getFirstPageBeanList();
				if( bh.getMaxRows() != 1 ) {
					//alarm_idがない場合その他項目になる
					daBean.setAlarmId( DB_ALARM_ID_OTHERS );
				} else {
					//ここに到達した時点でMA_ALARMに登録済のアラームである
					//原則として引数の内容を優先し、引数がない場合はMA_ALARMを優先する。
					maBean = (MaAlarmBean)bh.getBeanIterator().next();
					//アラーム種別名称
					if( daBean.getAlarmTypeNa() == null || daBean.getAlarmTypeNa().trim().equals("") ){
						daBean.setAlarmTypeNa( maBean.getAlarmTypeNa() );
					}
					//内容
					if( daBean.getContentTx() == null || daBean.getContentTx().trim().equals("") ){
						daBean.setContentTx( maBean.getContentTx() );
					}
					//有効終了日
					if( daBean.getYukoSyuryoDt() == null || daBean.getYukoSyuryoDt().trim().equals("") ){
						MDWareDateUtility mdDate = new MDWareDateUtility( RMSTDATEUtil.getMstDateDt());
						daBean.setYukoSyuryoDt( mdDate.addDateTime( Calendar.DAY_OF_MONTH,(int)maBean.getPeriod() ).toString() );
					}
					//URL
					if( daBean.getUrlTx() == null || daBean.getUrlTx().trim().equals("") ){
						daBean.setUrlTx( maBean.getUrlTx() );
					}
					//色
					if( daBean.getColorTX() == null || daBean.getColorTX().trim().equals("") ){
						daBean.setColorTX( maBean.getColorTX() );
					}
				}
			}
			//アラーム番号をセット
			daBean.setAlarmNb( MDWareSeq.nextVal("alarm_nb", daBean.getInsertUserId() ) );
			//時間をセット
			daBean.setInsertTs( AbstractMDWareDateGetter.getDefaultProductTimestamp( DB_SCHEMA_NAME ) );
			daBean.setUpdateTs( AbstractMDWareDateGetter.getDefaultProductTimestamp( DB_SCHEMA_NAME ) );
			
			//削除フラグセット
			daBean.setDelFg( DtAlarmDelFgDic.MISAKUJO.getCode() );
			
			daBean.setDestinationBusyoCd(daBean.getDestinationBusyoCdTX());
			daBean.setDestinationTenpoCd(daBean.getDestinationTenpoCdTX());
			
			//リストに情報をセット
			li.add( daBean );
			
			bh = new BeanHolder( daDM );
			//削除実行
			bh.insertBeans( li );
			bh.commit();
		}catch (Exception e){
			//ロールバック
			bh.rollback();
			//ログ出力
			if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
				BatchLog.getInstance().getLog().info( "アラームの新規作成処理に失敗しました" + e.toString() );
			} else {
				StcLog.getInstance().getLog().info( "アラームの新規作成処理に失敗しました" + e.toString() );
			}
			//return new DtAlarmBean();
			return null;
		}finally{
			bh.close();
		}
		
		//終了ログ	
		if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
			BatchLog.getInstance().getLog().info( daBean.getAlarmNb() + "番でアラームの新規作成処理に成功しました" );
		} else {
			StcLog.getInstance().getLog().info( daBean.getAlarmNb() + "番でアラームの新規作成処理に成功しました" );
		}	
		return daBean;
	}
	
	/**
	 * <p>引数のdelKeyStrをキーにしてアラームの論理削除を行うメソッド</p>
	 * @param delKeyStr 削除キー
	 * @param userId 更新者ＩＤ
	 * @param processKb mdware.portal.dictionary.AlarmProcessKbDicの値
	 * @return boolean
	 */
	public boolean removeAlarmMessage( String delKeyStr, String userId, String processKb ) {
		
		//開始ログ
		if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
			BatchLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除処理を開始します" );
		} else {
			StcLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除処理を開始します" );
		}
		
		MSTB907010_DtAlarmDMForSyohinMasterHenkoList daDM 		= new MSTB907010_DtAlarmDMForSyohinMasterHenkoList();			//更新用DM
		MSTB907010_DtAlarmBeanForSyohinMasterHenkoList daBean 	= new MSTB907010_DtAlarmBeanForSyohinMasterHenkoList();		//削除用Bean
		BeanHolder bh 		= new BeanHolder( daDM );	//削除用BeanHolder
		List li 			= new ArrayList();			//削除リスト
				
		try {
			//論理削除するアラームの削除キーをセットする
			daBean.setDelKeyTx( delKeyStr );
			//削除フラグオン
			daBean.setDelFg( DtAlarmDelFgDic.SAKUJOZUMI.getCode() );
			//更新者ＩＤと時刻をセットする
			daBean.setUpdateUserId( userId );
			daBean.setUpdateTs( AbstractMDWareDateGetter.getDefaultProductTimestamp("rbsite_ora") );
				
			//リストに情報をセット
			li.add( daBean );
			
			//削除実行
			bh.updateBeans( li );
			bh.commit();
		}catch (Exception e){
			//ロールバック
			bh.rollback();
			//ログ出力
			if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
				BatchLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除処理に失敗しました" + e.toString() );
			} else {
				StcLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除処理に失敗しました" + e.toString() );
			}
			return false;
		}finally{
			bh.close();
			bh = null;
		}
		
		//終了ログ	
		if( AlarmProcessKbDic.BATCH.getCode().equals( processKb ) ){
			BatchLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除に成功しました" );
		} else {
			StcLog.getInstance().getLog().info( delKeyStr + "のアラームの論理削除に成功しました" );
		}	
		return true;
	}


}
