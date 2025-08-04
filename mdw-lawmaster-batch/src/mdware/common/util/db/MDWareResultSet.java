package mdware.common.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * RBSITE用ユーティリティー（ResultSetの汎用クラス）。 <BR>
 * ResultSetでは、数値項目がnullの時返ってくる値が実装依存になってしまいます。 <BR>
 * そのため、nullであるという事が分からないまま処理を流してしまう事が考えられます。 <BR>
 * 数値項目がnullの時は、int、long、doubleの取る事の出来る最小値、または最大値に置き換えて <BR>
 * 返す事が出来るようにメソッドを用意しています。 <BR>
 * CLOBやBLOGは実装していないので、もし必要な時はこのクラスを継承して作成してください。 <BR>
 * 
 * @author telema_yugen777
 */
public class MDWareResultSet
{
	private ResultSet rest = null;

	/**
	 * コンストラクタ。
	 * 
	 * @param rest
	 */
	public MDWareResultSet( ResultSet rest )
	{
		this.rest = rest;
	}

	/**
	 * ResultSetをクローズする。
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException
	{
		rest.close();
	}

	/**
	 * 次の行が存在するかを返す。
	 * 
	 * @return true:次の行が存在する false:次の行が存在しない
	 * @throws SQLException
	 */
	public boolean next() throws SQLException
	{
		return rest.next();
	}

	/**
	 * 結果セットから文字列を取得する。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した文字列
	 * @throws SQLException
	 */
	public String getString( int pos ) throws SQLException
	{
		return rest.getString( pos );
	}

	/**
	 * 結果セットから文字列を取得する。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した文字列
	 * @throws SQLException
	 */
	public String getString( String key ) throws SQLException
	{
		return rest.getString( key );
	}

	/**
	 * 結果セットから文字列を取得する。 取得した文字列がnullの時は、空文字を返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した文字列
	 * @throws SQLException
	 */
	public String getStringNullIsNoStr( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return "";
		}
		return ret;
	}

	/**
	 * 結果セットから文字列を取得する。 取得した文字列がnullの時は、空文字を返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した文字列
	 * @throws SQLException
	 */
	public String getStringNullIsNoStr( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return "";
		}
		return ret;
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getInt( int pos ) throws SQLException
	{
		return rest.getInt( pos );
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getInt( String key ) throws SQLException
	{
		return rest.getInt( key );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsZero( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return 0;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsZero( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return 0;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はInteher.MIN_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsMin( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Integer.MIN_VALUE;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はInteger.MIN_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsMin( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Integer.MIN_VALUE;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はInteger.MAX_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsMax( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Integer.MAX_VALUE;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はInteger.MAX_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public int getIntNullIsMax( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Integer.MAX_VALUE;
		}
		return Integer.parseInt( ret );
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLong( int pos ) throws SQLException
	{
		return rest.getLong( pos );
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLong( String key ) throws SQLException
	{
		return rest.getLong( key );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsZero( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return 0;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsZero( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return 0;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はLong.MIN_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsMin( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Long.MIN_VALUE;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はLong.MIN_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsMin( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Long.MIN_VALUE;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はLong.MAX_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsMax( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Long.MAX_VALUE;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はLong.MAX_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public long getLongNullIsMax( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Long.MAX_VALUE;
		}
		return Long.parseLong( ret );
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDouble( int pos ) throws SQLException
	{
		return rest.getDouble( pos );
	}

	/**
	 * 結果セットから値を取得する。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDouble( String key ) throws SQLException
	{
		return rest.getDouble( key );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsZero( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return 0;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時は０を返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsZero( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return 0;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はDouble.MIN_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsMin( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Double.MIN_VALUE;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はDouble.MIN_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsMin( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Double.MIN_VALUE;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はDouble.MAX_VALUEを返す。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsMax( int pos ) throws SQLException
	{
		String ret = rest.getString( pos );
		if( ret == null )
		{
			return Double.MAX_VALUE;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから値を取得する。 nullの時はDouble.MAX_VALUEを返す。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した値
	 * @throws SQLException
	 */
	public double getDoubleNullIsMax( String key ) throws SQLException
	{
		String ret = rest.getString( key );
		if( ret == null )
		{
			return Double.MAX_VALUE;
		}
		return Double.parseDouble( ret );
	}

	/**
	 * 結果セットから日時を取得する。
	 * 
	 * @param pos 結果セットのカラム位置
	 * @return 取得した日時
	 * @throws SQLException
	 */
	public Timestamp getTimestamp( int pos ) throws SQLException
	{
		return rest.getTimestamp( pos );
	}

	/**
	 * 結果セットから日時を取得する。
	 * 
	 * @param key 結果セットのカラムキー
	 * @return 取得した日時
	 * @throws SQLException
	 */
	public Timestamp getTimestamp( String key ) throws SQLException
	{
		return rest.getTimestamp( key );
	}
}