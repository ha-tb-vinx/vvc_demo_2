package mdware.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import mdware.common.dictionary.LGyosyuDictionary;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;

/**
 * <p>タイトル: 業種サーチャー</p>
 * <p>説明:業種を検索します。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Tanigawa
 * @version 1.00 2005/01/13 tanigawa
 */
//現在は、大業種コードの探索のみを行っています
public class GyosyuSearcher {
	/**
	 * 大業種コードを取得する
	 * @param String 小業種コード
	 * @return String 大業種コード
	 * @throws Exception
	 */
	public static String getLGyosyuCd(String s_gyosyu_cd) throws Exception {
		return getLGyosyuCd(new DataBase("rbsite_ora"), s_gyosyu_cd);
	}

	/**
	 * 大業種コードを取得する
	 * @param DataBase  使用するコネクションを指定することができる（主にコネクションプールを使用しないバッチ処理時に使用する）
	 * @param String 小業種コード
	 * @return String 大業種コード
	 * @throws Exception
	 */
	private static String getLGyosyuCd(DataBase database, String s_gyosyu_cd) throws Exception {

		ResultSet resultSet = null;	
		String l_gyosyu_cd  = null;
		try{
			
			StringBuffer sb = new StringBuffer("");

			// 暗黙のトランザクション開始
			database.commit();

			sb.append("SELECT ");
			sb.append("    l_gyosyu_cd ");
			sb.append("FROM ");
			sb.append("    ma_s_gyosyu ");
			sb.append("where ");
			sb.append("    s_gyosyu_cd = '"+s_gyosyu_cd+"' ");
			resultSet = database.executeQuery(sb.toString());
			if (!resultSet.next()) {
				StcLog.getInstance().getLog().fatal("大業種コードがありません。検索条件として使用した小業種コード:" + s_gyosyu_cd);
				l_gyosyu_cd = null;
			}else{
				l_gyosyu_cd = resultSet.getString("l_gyosyu_cd");
			}
		} catch (Exception e) {
			StcLog.getInstance().getLog().fatal("大業種コードの取得に失敗しました。検索条件として使用した小業種コード:" + s_gyosyu_cd);
			StcLog.getInstance().getLog().fatal(e.getMessage());
            try {
                resultSet.close();
            } catch (SQLException sqle) {
            
            }
			database.close();
			database = null;
			resultSet = null;

			throw e;
		}
		
		//大業種コードを返す
		return l_gyosyu_cd;
	}

	/**
	 * 小業種コードが食品の大業種に属するか否かを判定する
	 * @param DataBase  使用するコネクションを指定することができる（主にコネクションプールを使用しないバッチ処理時に使用する）
	 * @param String 小業種コード
	 * @return boolean 食品の大業種コードか否か
	 * @throws Exception
	 */
	public static boolean isFood(String s_gyosyu_cd) throws Exception {

		String l_gyosyu_cd = getLGyosyuCd(s_gyosyu_cd);
		if( l_gyosyu_cd == null )
			return false;

//		//食品
//		public static LGyosyuDictionary SYOKUHIN 	= new LGyosyuDictionary("0033","食品");
		if( l_gyosyu_cd.equals(LGyosyuDictionary.SYOKUHIN.getCode()) ){
			return true;
		}

		return false;
	}

	/**
	 * 小業種コードが衣料の大業種に属するか否かを判定する
	 * @param DataBase  使用するコネクションを指定することができる（主にコネクションプールを使用しないバッチ処理時に使用する）
	 * @param String 小業種コード
	 * @return boolean 衣料の大業種コードか否か
	 * @throws Exception
	 */
	public static boolean isIryo(String s_gyosyu_cd) throws Exception {

		String l_gyosyu_cd = getLGyosyuCd(s_gyosyu_cd);
		if( l_gyosyu_cd == null )
			return false;

//		//衣料
//		public static LGyosyuDictionary IRYO 	   	= new LGyosyuDictionary("0011","衣料");
//		public static LGyosyuDictionary FUKUSYOKU	= new LGyosyuDictionary("0044","服飾");
//		public static LGyosyuDictionary SPORTS	= new LGyosyuDictionary("0061","スポージアム");
		if(
		l_gyosyu_cd.equals(LGyosyuDictionary.IRYO.getCode()) 	|| 
		l_gyosyu_cd.equals(LGyosyuDictionary.SPORTS.getCode()) 	|| 
		l_gyosyu_cd.equals(LGyosyuDictionary.FORUSOME.getCode())
		){
			return true;
		}

		return false;
	}

	/**
	 * 小業種コードが住生活の大業種に属するか否かを判定する
	 * @param DataBase  使用するコネクションを指定することができる（主にコネクションプールを使用しないバッチ処理時に使用する）
	 * @param String 小業種コード
	 * @return boolean 住生活の大業種コードか否か
	 * @throws Exception
	 */
	public static boolean isJuSeikatu(String s_gyosyu_cd) throws Exception {

		String l_gyosyu_cd = getLGyosyuCd(s_gyosyu_cd);
		if( l_gyosyu_cd == null )
			return false;
			
//		//住生活
//		public static LGyosyuDictionary JUKYO		= new LGyosyuDictionary("0022","住居");
//		public static LGyosyuDictionary FORUSOME	= new LGyosyuDictionary("0077","フォルサム");
		if(
		l_gyosyu_cd.equals(LGyosyuDictionary.JUKYO.getCode()) || 
		l_gyosyu_cd.equals(LGyosyuDictionary.FORUSOME.getCode())
		){
			return true;
		}

		return false;
	}


	//テスト用メインメソッド
	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";

		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);
		try {
			System.out.println(GyosyuSearcher.getLGyosyuCd("0074"));
			String s_gyosyu_cd = "0074";
			System.out.println("食品　："+GyosyuSearcher.isFood(s_gyosyu_cd));
			System.out.println("衣料　："+GyosyuSearcher.isIryo(s_gyosyu_cd));
			System.out.println("住生活："+GyosyuSearcher.isJuSeikatu(s_gyosyu_cd));
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("err");
		}
	}

}
