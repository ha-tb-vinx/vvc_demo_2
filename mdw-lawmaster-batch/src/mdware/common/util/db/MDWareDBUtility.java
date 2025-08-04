package mdware.common.util.db;

import jp.co.vinculumjapan.stc.util.db.CollectConnections;
import jp.co.vinculumjapan.stc.util.text.ReplaceString;

/**
 * RBSITE用ユーティリティー（ＤＢサニタイズ用クラス）。 <BR>
 * ＳＱＬに文字列をセットする時は、このクラスを利用する事をお勧めする。 <BR>
 * 次のようなＳＱＬを用意したとします。 <BR>
 * select * from users where user = 'DATA_AREA';
 * このDATA_AREAにはブラウザから渡された情報を埋め込むとした時 ブラウザからは、a' or '1' = '1
 * が渡されるとＳＱＬは次のようになります。 select * from users where user = 'a' or '1' = '1';
 * 結果は全てのデータが抜けてしまいます。 これを防ぐためのクラスです。 具体的には、ブラウザから渡された文字列を次のようにします。 select *
 * from users where user = 'a'' or ''1'' = ''1'; このようにする事で、情報をすべて抜かれる事を防ぎます。
 * 
 * @author telema_yugen777
 */
public class MDWareDBUtility
{
	private MDWareDBUtility()
	{
		//間違ってインスタンス化しないようにしている。
	}

	/**
	 * サニタイズを行います。
	 * 
	 * @param val サニタイズしたい文字列
	 * @return サニタイズされた文字列
	 */
	public static String sanitize( String val )
	{
		return ReplaceString.replaceAll( val, "'", "''" );
	}
	
	/**
	 * スキーマ名を取得します。
	 * 
	 * @return String スキーマ名
	 */
	public static String getSchemaName()
	{
		String schemaName 	= "";
		String workStr		= "";
		
		workStr = CollectConnections.getInstance().getDBSetting("rbsite_ora").getUrl();
		
		for( int i = 0; i < workStr.length(); i++ ) {
			schemaName += workStr.substring( i, i + 1 );
			if( workStr.substring( i, i + 1 ).equals("/") ) {
				schemaName = "";
			}
		}
		
		return schemaName;
	}

	/**
	 * ユーザー名を取得します。
	 * 
	 * @return String ユーザー名
	 */
	public static String getUser()
	{
		String user 	= "";
		
		user = CollectConnections.getInstance().getDBSetting("rbsite_ora").getUser();
		
		return user;
	}
}