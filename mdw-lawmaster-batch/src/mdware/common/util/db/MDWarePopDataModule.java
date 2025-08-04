package mdware.common.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.text.Unicode;

import mdware.common.util.date.*;
/**
 * <p>タイトル: データモジュール</p>
 * <p>説明: ページ移動未対応のＤＭの基底クラス</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * <PRE>
 * 逐次呼び出し型のＤＭです。
 * 少量のデータのみの抽出の時に使用します。
 * </PRE>
 * @author Yoshimoto
 * @version 1.0
 */
public abstract class MDWarePopDataModule
{
	private static final StcLog stcLog = StcLog.getInstance();
	private MDWarePopDataBase database = null;
	private String sql = "";
	private Map map = null;
	private int maxRows = 0;
	protected String encoding;
	private String databaseName = null;
	private int cntKb = 0;//0はResultset.next()でカウント、1はカーソル最終行移動→カーソル位置取得、2はcount(*)のselectを発行
	private List allList = new ArrayList();

	/**
	 * コンストラクタ
	 * cntNbはMDWarePopDataModuleの独自仕様で件数取得の方法を定義する
	 * @param dbname 設定ファイルのＤＢの名前
	 */
	public MDWarePopDataModule(String dbname, int cntKb )
	{
		databaseName = dbname;
		database = new MDWarePopDataBase( dbname );
		this.encoding = database.getEncoding( dbname );
		this.cntKb = cntKb;
	}
	/**
	 * ＤＢから抽出したデータ１件をＢＥＡＮとしてインスタンス化する(ユーザが作成する)。
	 * @param holder TableRowHolderInterface 検索結果
	 * @return Object
	 * @throws SQLException
	 */
	protected abstract Object instanceBean( ResultSet rest )
		throws SQLException;

	/**
	 * 継承先のＤＭからＳＱＬを生成してからもらう
	 * @param map DataHolderから抜き出したＭａｐ
	 * @return ＳＱＬ
	 */
	protected abstract String getSelectSql( Map map );
	
	/**
	 * 継承先のＤＭからＳＱＬを生成してからもらう(カウント値取得用)
	 * @param map DataHolderから抜き出したＭａｐ
	 * @return ＳＱＬ
	 */
	protected abstract String getSelectCountSql( Map map );

	/**
	 * 検索用ＳＱＬを生成するための元ねたが入っているDataHolderをセットする
	 * @param dh DataHolder
	 */
	public void setDataHolder( DataHolder dh )
	{
		map = dh.getParameterMap();
	}

	/**
	 * ＤＢから条件指定でデータを抽出する(ユーザが作成する)。
	 * ＳＱＬを生成する。
	 * @param map Map SQLのWHERE区を組み立てるための情報が入っている。
	 * @return
	 */
	public List getBean()
		throws SQLException
	{
		maxRows = 0;
		List list = new ArrayList();
		sql = getSelectSql( map );
//20030328 mod yoshi
//SQLExceptionが発生した時にConnectionを解放出来ていない事による対応
//		ResultSet rest = database.executeQuery( sql );
//		while( rest.next() )
//		{
//			maxRows++;
//			list.add( instanceBean( rest ) );
//		}
//		database.close();
		try
		{
			ResultSet rest = database.executeQuery( sql );
			while( rest.next() )
			{
				maxRows++;
				list.add( instanceBean( rest ) );
//				list.add( instanceBean( new StcLibResultSet(rest, this.encoding) ) );
			}
		}
		finally
		{
			database.close();
		}
		return list;
	}
	/**
	 * 指定ページのＢＥＡＮのリストを返す
	 * @param fromRowNumber 取得開始の行番号
	 * @param toRowNumber 取得終了の行番号
	 * @return List
	 * @throws SQLException
	 */
	public List getBean( int fromRowNumber, int toRowNumber )
		throws SQLException
	{
		maxRows = 0;
		List list = new ArrayList();
		sql = getSelectSql( map );
//20030328 mod yoshi
//SQLExceptionが発生した時にConnectionを解放出来ていない事による対応
//		ResultSet rest = database.executeQuery( sql );
//		for(; maxRows < fromRowNumber && rest.next() ; maxRows++ );
//		for(; maxRows < toRowNumber && rest.next() ; maxRows++ )
//			list.add( instanceBean( rest ) );
//		for(; rest.next() ; maxRows++ );
//		database.close();
		try
		{
			ResultSet rest = database.executeQuery( sql );
			rest.absolute( fromRowNumber );
			maxRows = fromRowNumber;
			boolean isNext = true;
			for(; isNext && maxRows < fromRowNumber && (isNext = rest.next()) ; maxRows++ );
			for(; isNext && maxRows < toRowNumber && (isNext = rest.next()) ; maxRows++ ){
				list.add( instanceBean( rest ) );
				if( cntKb == 3 ) allList.add( instanceBean( rest ) );
			}
			//従来は↓の処理で時間を食っていたので３通りの方法から選択させるオプションをつける
			//System.out.println(AbstractMDWareDateGetter.getDefaultProductTimestamp("開始"+"rbsite_ora"));
			if( cntKb == 2 ){
				String sql2 = getSelectCountSql( map );
				ResultSet rest2 = database.executeQuery( sql2 );
				if( rest2.next() ) maxRows = rest2.getInt("cnt");
			}else if( cntKb == 1 ){
				if( maxRows != 0 ){
					rest.last();
					maxRows = rest.getRow();
				}
			}else{
				for(; isNext && rest.next() ; maxRows++ ){
					if( cntKb == 3 ) allList.add( instanceBean( rest ) );
				}
			}
			
			//System.out.println(AbstractMDWareDateGetter.getDefaultProductTimestamp("終了"+"rbsite_ora"));
		}
		catch(Exception e){
			e.printStackTrace();
			stcLog.getLog().error("MDWarePopDataModule.getBeanでエラーが発生しています" + e.toString());
		}
		finally
		{
			database.close();
		}
		return list;
	}
	/**
	 * allListを返す
	 * @return
	 */
	public List getAllList(){
		return this.allList;
	}
	/**
	 * 検索結果の取得件数を返す
	 * @return int
	 */
	public int getMaxRows()
	{
		return maxRows;
	}
	/**
	 * 反映を行う
	 */
	public void commit()
	{
		database.commit();
	}
	/**
	 * 反映を取り消す
	 */
	public void rollback()
	{
		database.rollback();
	}
	/**
	 * 接続を開放する
	 */
	public void close()
	{
		database.close();
	}
	/**
	 * 更新のＳＱＬを投げる
	 * @param modSql 挿入、更新、削除のＳＱＬ
	 * @return boolean
	 */
	protected boolean modifyBean( String modSql )
	{
		try
		{
			database.executeUpdate( modSql );
		}
		catch(SQLException e )
		{
			return false;
		}
		return true;
	}

	/**
	 * 削除のＳＱＬを返す
	 * @param bean 削除のＳＱＬを生成するために必要なBEAN
	 * @return String 削除のSQL
	 */
	public abstract String getDeleteSql( Object bean );
	/**
	 * 挿入のＳＱＬを返す
	 * @param bean 挿入のＳＱＬを生成するために必要なBEAN
	 * @return String 挿入のSQL
	 */
	public abstract String getInsertSql( Object bean );
	/**
	 * 更新のＳＱＬを返す
	 * @param bean 更新のＳＱＬを生成するために必要なBEAN
	 * @return String 更新のSQL
	 */
	public abstract String getUpdateSql( Object bean );

	/**
	 * 削除を行う
	 * @param bean 削除のＳＱＬを生成するために必要なBEAN
	 * @return boolean 成功か失敗
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean deleteBean( Object bean )
	{
		return modifyBean( getDeleteSql( bean ) );
	}

	/**
	 * 挿入を行う
	 * @param bean 挿入のＳＱＬを生成するために必要なBEAN
	 * @return boolean 成功か失敗
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean insertBean( Object bean )
	{
		return modifyBean( getInsertSql( bean ) );
	}
	/**
	 * 更新を行う
	 * @param bean 更新のＳＱＬを生成するために必要なBEAN
	 * @return boolean 成功か失敗
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean updateBean( Object bean )
	{
		return modifyBean( getUpdateSql( bean ) );
	}

	/**
	 * 連番の取得を行う。
	 * 最大数を返す。
	 * @param column 連番のカラム名
	 * @param table テーブル名
	 * @return String 最大数を返す。(longでは返さない。間違って文字列のカラムを指定すると数字に変換できないと例外を返すだろうが理由がすぐ分からない)
	 */
	public String getNextSequence( String column, String table )
	{
		String sql = "select max(" + column + ") as seq from " + table;
		String num = "0";
		try
		{
			ResultSet rest = database.executeQuery( sql );
			if( rest.next() )
				if( ( num = rest.getString("seq") ) == null )
					num = "0";
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
		return num;
	}

	/**
	 * SQL生成時に単一引用符をwhere句で利用する場合"'"→"''"に置き換える。
	 * @param 文字列（通常はwhere句内で利用されるSQL文のホスト変数内の文字列）
	 * @return String "'"→"''"に置き換わった文字列
	 * @author nob
	 */
	public String singleQuotesfilter(String src){
		if(src == null)
			return new String("");

		StringBuffer dest = new StringBuffer("");
		char c;

		for(int i = 0, n = src.length(); i < n; i++){
			if((c = src.charAt(i)) == '\'')
				dest.append('\'');

			dest.append(c);
		}

		return dest.toString();
	}

	/**
	 * ＤＢの作成した会社名を返す。
	 * @return
	 */
	public String getDatabaseProductName()
	{
//20030220 mod yoshi
//コネクションプールの数が合わなくなる事への対応
		String ret = database.getDatabaseProductName();
		database.close();
		return ret;

//		return database.getDatabaseProductName();
	}
	
	public String encodingString( String val )
	{
		if( val == null )
			return null;
//		20041116 mod yoshi M2様対応 設定ファイルでコード変換を行うように変更
//		val = Unicode.convert(val, Unicode.ACCESS_DB_STRING, encoding);
//		val = Unicode.convert(val);
		String bef = val;
		val = Unicode.convertFromDatabase(val, databaseName);
		String aft = val;
		//System.out.println(bef + "->" + aft);
		return val;
	}
}
