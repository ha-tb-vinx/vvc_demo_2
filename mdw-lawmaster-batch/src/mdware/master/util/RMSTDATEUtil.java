package mdware.master.util;

import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.resorces.util.ResorceUtil;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.util.bean.RMSTDATEBean;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: マスタ管理用日付テーブルからマスタ日付を取得する</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author k.arai
 * @version 1.0
 */

public class RMSTDATEUtil {

	/**
	 * マスタ日付を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String マスタ日付
	 */

	//20060830 hiu@vjc パフォーマンス改善のため、getMstDateDt で都度データベースから取得していたデータをキャッシュするようにする
	//getMasDateDtFromDataBaseはＤＢから時刻を取得する。AbstractMasterCommand から　コールされる（画面遷移時にＤＢを参照する）
	
	
	//時刻のキャッシュ
	static  String mstDate="";



	public static String getMstDateDt()
	{
		if(RMSTDATEUtil.mstDate.length()==0){
			RMSTDATEUtil.getMasDateDtFromDataBase(); 
		}
		//String s=RMSTDATEUtil.mstDate;
		//System.out.println("マスタ時刻をかえします"+s);			
		return RMSTDATEUtil.mstDate;

	}

	//引数をセットすることでDBアクセスをなくすようメソッド追加
	public  static void getMasDateDtFromDataBase( String str ){
		if( str == null || str.trim().length() != 8 ) {
			getMasDateDtFromDataBase();
		} else {
			synchronized( RMSTDATEUtil.mstDate ) {		
				RMSTDATEUtil.mstDate= str;
			}
		}
	}
	
	public  static void getMasDateDtFromDataBase(){
		//System.out.println("マスタ時刻をデータベースから取得します");		
//		DataHolder dh = new DataHolder();
//		RMSTDATEBean rmstdateBean = RMSTDATEBean.getRMSTDATEBean(dh);
//		synchronized(RMSTDATEUtil.mstDate){		
//			RMSTDATEUtil.mstDate= rmstdateBean.getMstDateDt() ;
//		}
		String mstDateDtStr = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.ONLINE_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
		synchronized(RMSTDATEUtil.mstDate){		
			RMSTDATEUtil.mstDate= mstDateDtStr ;
		}
		//System.out.println("マスタ時刻をデータベースから取得しました");
	}


	/**
	 * バッチ日付を取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String バッチ日付
	 */
	public static String getBatDateDt()
	{
//		DataHolder dh = new DataHolder();
//		RMSTDATEBean rmstdateBean = RMSTDATEBean.getRMSTDATEBean(dh);
//		return( rmstdateBean.getBatDateDt() );
		return ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
	}

	/**
	 * 使用可能フラグを取得する
	 * 取得できないとき、複数件のときはNULLを返す。
	 * @return String 使用可能フラグ
	 */
	public static String getMstUseFg()
	{
//		DataHolder dh = new DataHolder();
//		RMSTDATEBean rmstdateBean = RMSTDATEBean.getRMSTDATEBean(dh);
//		return( rmstdateBean.getMstUseFg() );
		return ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.ONLINE_FG,mst000101_ConstDictionary.SUBSYSTEM_DIVISION);
	}
	
}