package mdware.common.util.system;

import java.sql.ResultSet;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import mdware.common.dictionary.ServiceStatus;
import mdware.common.dictionary.UserSyubetu;
import mdware.portal.dictionary.RRiyoUserRiyoUserSyubetuKbDic;

/**
 * <p>タイトル: MDWareSeisenEDIUseFgGetter</p>
 * <p>説明: 生鮮EDIの使用可能可否をBOOLEANで返す</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author k.nakazawa
 * @version 1.0
 */
 
 public class MDWareSeisenEDIUseFgGetter {
 	
	//このクラスは生鮮EDIのrbsite.service.S08.S080101BatchCtlを模倣しました。
	//営業支援側との差異は特に考慮せず、生鮮側のルールで判断した場合に
	//使用可能状態であるか否かをBOOLEANで返します。falseの場合、使用不可時間帯です。
	
	/**
	 * バッチ制御テーブルの値よりログインの可否を判断する
	 * @param 
	 * @return ログインの可否
	 */
	public static boolean getSeisenUseBool( String riyoUserSyubetuStr ) {
		
		String batch = "";
		String service = "";
		String end_sts = "";
		
		DataBase dataBase = null;
		
		try{
			dataBase = new DataBase("rbsite_ora");
			
			StringBuffer sb = new StringBuffer();
		
			sb.append("select ");
			sb.append(" batch, ");
			sb.append(" service, ");
			sb.append(" end_sts ");
			sb.append("from ");
			sb.append(" seisen_batch_ctl");
			
			ResultSet rs = dataBase.executeQuery( sb.toString() );
			
			if( rs.next() ) {
				batch   = rs.getString("batch");
				service = rs.getString("service");
				end_sts = rs.getString("end_sts");
			} else {
				return false;
			}
			
			//取引先の場合
			if( riyoUserSyubetuStr.equals( RRiyoUserRiyoUserSyubetuKbDic.TORIHIKISAKI.getCode() ) ) {
				
				if( service.equals( ServiceStatus.SERVICE_START1.getCode() ) ) {
					//サービス状態が「1」
					return true;
				} else if ( service.equals( ServiceStatus.SERVICE_START2.getCode() ) ) {
					//サービス状態が「2」
					return true;
				} else if ( service.equals( ServiceStatus.SERVICE_STOP.getCode() ) ) {
					//サービス状態が「9」
					return false;
				} else if ( service.equals( ServiceStatus.SERVICE_START.getCode() ) ) {
					//サービス状態が「0」
					return true;
				} else {
					//サービス状態が上記以外の場合
					return false;
				}
			} else if ( riyoUserSyubetuStr.equals( RRiyoUserRiyoUserSyubetuKbDic.BUYER.getCode() ) ) {
				//バイヤー、店舗の場合
				if( service.equals( ServiceStatus.SERVICE_START.getCode() ) ) {
					//サービス状態が「0」
					return true;
				} else {
					//サービス状態が「0」以外の場合
					return false;
				}
			}
		} catch ( Exception e ) {
			
			StcLog.getInstance().getLog().error( "サービス状態取得処理でエラーが発生しました。" + e );
			return false;
		} finally {
			dataBase.close();
		}dataBase = null;

		return true;
	}
 }