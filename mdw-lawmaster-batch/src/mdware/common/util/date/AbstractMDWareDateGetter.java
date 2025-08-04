package mdware.common.util.date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;
import jp.co.vinculumjapan.stc.util.db.DBStringConvert;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertDBTwo;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertOracle;
import jp.co.vinculumjapan.stc.util.db.kind.DBStringConvertSQLServer;
import mdware.common.util.db.MDWarePreparedDatabase;

/**
 * RBSITE用ユーティリティー（ＤＢ日時の取得）。 <BR>
 * オラクル、ＤＢ２、SQLSERVERのＤＢ日時を取得します。 <BR>
 * 拡張する事で、他社ＤＢの日時も取得が可能です。 <BR>
 * ※ＤＢ２はインスタンスを指定しなくてもアクセス出来る領域にDUALテーブルを作成し１行入れて利用してください。 <BR>
 * ※データベース名にはdatabase.propertiesで指定したデータベース名を入れてください。 <BR>
 * <BR>
 * オラクル：AbstractRbsiteDateGetter.getDefaultProductTimestamp("データベース名")でYYYYMMDDHHMISS文字列で取得できます。
 * <BR>
 * ＤＢ２：AbstractRbsiteDateGetter.getDefaultProductTimestamp("データベース名")でYYYYMMDDHHMISS文字列で取得できます。
 * <BR>
 * SQLSERVER：AbstractRbsiteDateGetter.getDefaultProductTimestamp("データベース名")でYYYYMMDDHHMISS文字列で取得できます。
 * <BR>
 * <BR>
 * デフォルトで用意されていないＤＢを利用する時は、このクラスを継承しgetTimestampSql()を実装してください。 <BR>
 * 
 * @author telema_yugen777
 */
public abstract class AbstractMDWareDateGetter
{
	/**
	 * 日時の取得を行う。
	 * オラクル、ＤＢ２、SQLSERVERのみサポートします。
	 * このメソッドを静的にアクセスすることで日時の取得が可能です。
	 * @param dbName database.properties内のDB名をセットします。
	 * @return YYYYMMDDHHMISS文字列で取得できます
	 * @throws SQLException
	 */
	
	//20060913   パフォーマンス改善の為　大幅な仕様変更　都度データベースから時刻を取得しないで　一回だけ取得してその差分を持ちます。　2度目以降はアプリサーバー時刻に差分を足してデータベース時刻の近似値を取ります
	
	//データベースから取得した時刻とアプリケーションサーバーから取得した時刻の差分を持つ
	static Integer diff=new Integer(-50000);
	
	public static String getDefaultProductTimestamp(String dbName) throws SQLException
	{
		StringBuffer b= new StringBuffer ();
	
		

		
		if(AbstractMDWareDateGetter.diff.intValue()==-50000){
			//時刻が取得されていない
			
			String dbtime=AbstractMDWareDateGetter.getTimeFromDatabase(dbName);
			int year=Integer.parseInt(dbtime.substring(0,4));
			int month=Integer.parseInt(dbtime.substring(4,6))-1;
			int day=Integer.parseInt(dbtime.substring(6,8));
			int hour=Integer.parseInt(dbtime.substring(8,10));
			int minute=Integer.parseInt(dbtime.substring(10,12));	
			int second=Integer.parseInt(dbtime.substring(12,14));
			StcLog.getInstance().getLog().error("AbstractMDWareDateGetter＃getDefaultProductTimestampデータベースより時刻を取得しました"+year+":"+(month+1)+":"+day+":"+hour+":"+minute+":"+second );
			Calendar dbcal=Calendar.getInstance();
			
			dbcal.set(year, month, day, hour, minute , second);
			
			long dbtimelong=dbcal.getTimeInMillis();
			
			Calendar appcal=Calendar.getInstance();
			
			long apptimelong=appcal.getTimeInMillis() ;
			
			long temp=dbtimelong-apptimelong;
			
			int tmp2=(int)temp;
			

			
			Integer inttgr=new Integer (tmp2);
			
			synchronized(AbstractMDWareDateGetter.diff){
				AbstractMDWareDateGetter.diff= inttgr;
			}
			
			
				
		}
		//時刻取得済み
		
		Calendar appcal=Calendar.getInstance();
		
		long lngApptime=appcal.getTimeInMillis();
		

		
		appcal.setTimeInMillis(lngApptime+diff.longValue());
		
		  
		
		b.append(modInt(appcal.get(Calendar.YEAR), 4));
		b.append(modInt(appcal.get(Calendar.MONTH)+1, 2));
		b.append(modInt(appcal.get(Calendar.DATE), 2));
		b.append(modInt(appcal.get(Calendar.HOUR_OF_DAY), 2));
		b.append(modInt(appcal.get(Calendar.MINUTE), 2));
		b.append(modInt(appcal.get(Calendar.SECOND), 2));


		
	
		return b.toString();
	}
	
	static String getTimeFromDatabase(String dbName) throws SQLException {
		DataBase db = new DataBase(dbName);
		try
		{
			
			String product = db.getDatabaseProductName();
			DBStringConvert dbsc = DBStringConvert.getDBStringConvert(product);
			if( dbsc instanceof DBStringConvertDBTwo )
			{
				return DB2_DATE_GETTER.getDatabaseTimestamp(dbName);
			}
			else
			if( dbsc instanceof DBStringConvertOracle )
			{
				return ORACLE_DATE_GETTER.getDatabaseTimestamp(dbName);
			}
			else
			if( dbsc instanceof DBStringConvertSQLServer )
			{
				return SQLSERVER_DATE_GETTER.getDatabaseTimestamp(dbName);
			}
			throw new IllegalArgumentException("デフォルトではサポートされていないデータベースです。オラクル、ＤＢ２、ＳＱＬＳＥＲＶＥＲ以外はこのクラスを継承して日時取得クラスを作成してください。");
		}
		finally
		{
			db.close();
		}
	}
	
	/**
	 * データベースの日付を取得する。
	 * 
	 * @param dbName database.properties内のデータベース名
	 * @return String YYYYMMDDHHMISS
	 * @throws SQLException
	 */
	public String getDatabaseTimestamp( String dbName ) throws SQLException
	{
		String ret = null;
		MDWarePreparedDatabase pdb = null;
		try
		{
			pdb = new MDWarePreparedDatabase( dbName );
			pdb.setSql( getTimestampSql() );
			ResultSet rest = pdb.executeQuery();
			if( rest.next() )
			{
				ret = DateChanger.dateToString( rest.getTimestamp( 1 ) );
			}
			rest.close();
		}
		catch( SQLException sqle )
		{
			throw sqle;
		}
		finally
		{
			try
			{
				pdb.close();
			}
			catch( Exception e )
			{
			}
		}
		return ret;
	}

	/**
	 * 各社ＤＢに対応したSQLを返します。 <BR>
	 * select timestamp型 from テーブル名 の形式でSQLを作成してください。 <BR>
	 * 
	 * @return 日付取得用のＳＱＬ
	 */
	abstract protected String getTimestampSql();

	/**
	 * ＳＱＬＳＥＲＶＥＲ用の日時取得クラス。 <BR>
	 */
	private static final AbstractMDWareDateGetter SQLSERVER_DATE_GETTER = new AbstractMDWareDateGetter()
	{
		protected String getTimestampSql()
		{
			return "select getDate()";
		}
	};

	/**
	 * オラクル用の日時取得クラス。 <BR>
	 */
	private static final AbstractMDWareDateGetter ORACLE_DATE_GETTER = new AbstractMDWareDateGetter()
	{
		protected String getTimestampSql()
		{
			return "select sysdate from dual";
		}
	};

	/**
	 * ＤＢ２用の日時取得クラス。 <BR>
	 * インスタンスを指定しなくてもアクセス出来る所にDUALテーブル(ダミー)を作成し、１行入れて利用してください。 <BR>
	 */
	private static final AbstractMDWareDateGetter DB2_DATE_GETTER = new AbstractMDWareDateGetter()
	{
		protected String getTimestampSql()
		{
			return "select current timestamp from DUAL";
		}
	};
	
	public static String modInt(int arg, int len) {
		StringBuffer str = new StringBuffer(Integer.toString(arg));
		StringBuffer str0 = new StringBuffer();
		for (int i = 0; i < len; i++) {
			str0.append("0");
		}
		str0.append(str.toString());
		int l = str0.length();
		return str0.substring(l - len, l);
	}
}