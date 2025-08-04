package mdware.common.util.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

// 20020910 yoshi 改ページ機能を簡素化
// 20020910 yoshi キャッシュを外部からセットできるようにメソッドの追加

public class MDWarePopBeanHolder
{
	private static final StcLog stcLog = StcLog.getInstance();
	protected int rowsInPage = 0;
	protected int maxRows = 0;
	protected int currentPageNum = 0;
	protected List cacheBeanList = new ArrayList();
	protected List currentBeanList = new ArrayList();
	protected boolean cached = false;
	protected MDWarePopDataModule dataModule;

	/**
	 * JSPで使用するコンストラクタ(通常は使用しない)
	 */
	public MDWarePopBeanHolder()
	{
	}

	/**
	 * 通常使用するコンストラクタ
	 * @param dataModule
	 */
	public MDWarePopBeanHolder(MDWarePopDataModule dataModule)
	{
		this.dataModule = dataModule;
		this.setDataHolder( new DataHolder() );
	}

	/**
	 * sessionには貼り付けられてはいるけどＪＳＰが勝手に貼り付けたものかどうかを調べる。
	 * falseが返った時はＪＳＰが勝手に貼り付けたObject。
	 * trueが返った時は初期化を正常に行ったObject。
	 * @return boolean
	 */
	public boolean isInitialize()
	{
		if( dataModule == null )
			return false;

		return true;
	}

	/**
	 * ページ内の行数を指定する。
	 * 行数が０の時は改ページはしない。
	 * 行数を指定すると、行数で改ページを行う。
	 * @param rowsInPage
	 */
	public void setRowsInPage(int rowsInPage)
	{
		this.rowsInPage = rowsInPage;
	}

	/**
	 * 改ページを行うときに全件キャッシュするかどうかを指定する。
	 * @param cached
	 */
	public void setCache(boolean cached)
	{
		this.cached = cached;
	}

	/**
	 * キャッシュを一度クリアし取得しなおす。
	 * @throws SQLException
	 */
	public void refresh() throws SQLException
	{
//20030729 mod yoshi
//		cacheBeanList = null;
//		getSpecificationPageBeanList( currentPageNum );
		refresh( true );

// getSpecificationPageBeanListメソッドのオーバーライド対応　2002.08.30 nob
//
// getSpecificationPageBeanListメソッドのオーバーライドしている場合、基本的にdataModule.getMaxRows()で
// 取得する行数と、実際の行数は異なる。maxRowsフィールドはgetSpecificationPageBeanList内で更新している。
// ので以下のステートメントは不要。
//		maxRows = dataModule.getMaxRows();
	}

	/**
	 * キャッシュをリフレッシュする。
	 * 各ページ単位のリストを持ち直す。
	 * パラメータが true の時はキャッシュをクリアしてＤＢから取得しなおす。
	 * パラメータが false の時はキャッシュをクリアせずに最新の情報でページ内のリストを更新する。
	 * @since 20030729 yoshi
	 * @param isClear
	 * @throws SQLException
	 */
	public void refresh( boolean isClear ) throws SQLException
	{
		if( isClear )
			cacheBeanList = null;
		getSpecificationPageBeanList( currentPageNum );
	}

	/**
	 * ＤＭでＳＱＬを生成する元ねたとしてDataHolderをセットする。
	 * @param dataHolder
	 */
	public void setDataHolder(DataHolder dataHolder)
	{
		dataModule.setDataHolder( dataHolder );
	}

	/**
	 * 現在のページ情報が入ったListを返す。
	 * @return List
	 */
	public List getBeanList()
	{
		return currentBeanList;
	}

	/**
	 * 現在のページ情報が入ったIteratorを返す。
	 * @return Iterator
	 */
	public Iterator getBeanIterator()
	{
		return currentBeanList.iterator();
	}

	/**
	 * 現在のページ情報が入ったListIteratorを返す。
	 * @return ListIterator
	 */
	public ListIterator getBeanListIterator()
	{
		return currentBeanList.listIterator();
	}

	/**
	 * キャッシュ情報が入ったListを返す。
	 * @return List
	 */
	public List getCacheBeanList()
	{
		return this.cacheBeanList;
	}

	/**
	 * ＤＢアクセス以外でも改ページを行いたい時はこのメソッドを使用し対象をセットする。
	 * @param cacheBeanList List
	 */
	public void setCacheBeanList()
	{
		this.cacheBeanList = dataModule.getAllList();
		//this.maxRows = cacheBeanList.size();
		//this.currentPageNum = 0;
		//try
		//{
		//	this.getFirstPageBeanList();
		//}
		//catch(Exception e)
		//{
		//}
	}
	
	/**
	 * ＤＢアクセス以外でも改ページを行いたい時はこのメソッドを使用し対象をセットする。
	 * @param cacheBeanList List
	 */
	public void setCacheBeanList( List cacheBeanList )
	{
		this.cacheBeanList = cacheBeanList;
		this.maxRows = cacheBeanList.size();
		this.currentPageNum = 0;
		try
		{
			this.getFirstPageBeanList();
		}
		catch(Exception e)
		{
		}
	}

	/**
	 * キャッシュされたリストを指定したComparatorを使用しソートする
	 * @param comparator Comparator ソートの方法を知ったクラス
	 * @return List ソートした結果最初のページのListを返す
	 */
	public List sort( Comparator comparator )
		throws SQLException
	{
		Collections.sort(cacheBeanList, comparator); // ソート
		// 20040906 add yoshi
		// ソートしただけで１ページ分のデータには反映していなかったため追加した
		refresh(false);
		return this.getBeanList();
	}

	/**
	 * HTML,JSPにより渡されたmovePageパラメータの内容によりページの移動を行う。
	 * movePageは予約語として使用する。
	 * movePage=first	:最初のページに移動する。
	 * movePage=prev	:前のページに移動する。
	 * movePage=next	:次のページに移動する。
	 * movePage=last	:最後のページに移動する。
	 * もし、前のどれでもない時は初めてのアクセスとして判断し、
	 * setDataHolderを呼びdataHolderをセットし、
	 * 最初のページを呼び出す。
	 * @param dataHolder HTML,JSPから渡された情報を保持するクラス。
	 * @return List ＳＱＬを発行し抽出したBeanの入ったListを返す。
	 * @throws SQLException
	 */
	public List getPageBeanListFromDataHolder( DataHolder dataHolder )
		throws SQLException
	{
		String moveTo = dataHolder.getParameter("movePage");
		if( moveTo == null || moveTo.trim().length() == 0 )
		{
			this.setDataHolder( dataHolder );
			this.getFirstPageBeanList();
		}
		else
		if( moveTo.equals("first") || this.rowsInPage == 0 )
			this.getFirstPageBeanList();
		else
		if( moveTo.equals("prev") )
			this.getPrevPageBeanList();
		else
		if( moveTo.equals("next") )
			this.getNextPageBeanList();
		else
		if( moveTo.equals("last") )
			this.getLastPageBeanList();
		else
		{
			this.setDataHolder( dataHolder );
			this.getFirstPageBeanList();
		}

		return this.currentBeanList;
	}

	/**
	 * HTML,JSPにより渡されたmovePageパラメータの内容によりページの移動を行う。
	 * movePageは予約語として使用する。
	 * movePage=first	:最初のページに移動する。
	 * movePage=prev	:前のページに移動する。
	 * movePage=next	:次のページに移動する。
	 * movePage=last	:最後のページに移動する。
	 * getPageBeanListFromDataHolderとの違いはＤＢアクセスの有無であり
	 * ＤＢアクセス無しの改ページを行うにはＤＭのcntKbを3にする必要がある。
	 * @throws SQLException
	 */
	public List getPageBeanListFromDataHolderNotDBAccess( DataHolder dataHolder )
		throws SQLException
	{
		String moveTo = dataHolder.getParameter("movePage");
		if( moveTo == null || moveTo.trim().length() == 0 )
		{
			this.setDataHolder( dataHolder );
			this.getFirstPageBeanList();
		}
		else
		if( moveTo.equals("first") || this.rowsInPage == 0 )
			this.getFirstPageBeanListNotDBAccess();
		else
		if( moveTo.equals("prev") )
			this.getPrevPageBeanListNotDBAccess();
		else
		if( moveTo.equals("next") )
			this.getNextPageBeanListNotDBAccess();
		else
		if( moveTo.equals("last") )
			this.getLastPageBeanListNotDBAccess();
		else
		{
			this.setDataHolder( dataHolder );
			this.getFirstPageBeanList();
		}

		return this.currentBeanList;
	}
	
	/**
	 * 最初のページをＤＢアクセス無しで取得する。
	 * これはDataMojuleのcntKbが3でないと動かないので注意。
	 */
	public List getFirstPageBeanListNotDBAccess(){
		this.currentBeanList = new ArrayList();
		this.currentPageNum = 0;
		for(int i = 0; i < this.rowsInPage; i++){
			if( cacheBeanList.size() <= i+(currentPageNum*this.rowsInPage) ) return this.currentBeanList;
			currentBeanList.add( cacheBeanList.get(i+(currentPageNum*this.rowsInPage)) );
		}
		return this.currentBeanList;
	}
	
	/**
	 * 前のページをＤＢアクセス無しで取得する。
	 * これはDataMojuleのcntKbが3でないと動かないので注意。
	 */
	public List getPrevPageBeanListNotDBAccess(){
		this.currentBeanList = new ArrayList();
		this.currentPageNum--;
		if( this.currentPageNum < 0 )
			this.currentPageNum = 0;
		for(int i = 0; i < this.rowsInPage; i++){
			if( cacheBeanList.size() <= i+(currentPageNum*this.rowsInPage) ) return this.currentBeanList;
			currentBeanList.add( cacheBeanList.get(i+(currentPageNum*this.rowsInPage)) );
		}
		return this.currentBeanList;
	}
	
	/**
	 * 次のページをＤＢアクセス無しで取得する。
	 * これはDataMojuleのcntKbが3でないと動かないので注意。
	 */
	public List getNextPageBeanListNotDBAccess(){
		this.currentBeanList = new ArrayList();
		this.currentPageNum++;
		if( currentPageNum != 0 && currentPageNum >= ((int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1) )
			currentPageNum = (int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1;
		for(int i = 0; i < this.rowsInPage; i++){
			if( cacheBeanList.size() <= i+(currentPageNum*this.rowsInPage) ) return this.currentBeanList;
			currentBeanList.add( cacheBeanList.get(i+(currentPageNum*this.rowsInPage)));
		}
		return this.currentBeanList;
	}
	
	/**
	 * 最後のページをＤＢアクセス無しで取得する。
	 * これはDataMojuleのcntKbが3でないと動かないので注意。
	 */
	public List getLastPageBeanListNotDBAccess(){
		this.currentBeanList = new ArrayList();
		this.currentPageNum = (int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1;
		for(int i = 0; i < this.rowsInPage; i++){
			if( cacheBeanList.size() <= i+(currentPageNum*this.rowsInPage) ) return this.currentBeanList;
			currentBeanList.add( cacheBeanList.get(i+(currentPageNum*this.rowsInPage)));
		}
		return this.currentBeanList;
	}
	
	/**
	 * 最初のページを取得する。
	 * 改ページ無しの時はこのメソッドでListを取得する。
	 * 改ページの時はこのメソッドで最初のページを取得する。
	 * @return List
	 * @throws SQLException
	 */
	public List getFirstPageBeanList() throws SQLException
	{
		this.currentPageNum = 0;
		return getSpecificationPageBeanList(this.currentPageNum);
	}

	/**
	 * 前のページを取得する。
	 * 改ページ無しの時は使用しない。
	 * 改ページありのときは現在のページの前のページを取得する。
	 * @return List
	 * @throws SQLException
	 */
	public List getPrevPageBeanList() throws SQLException
	{
		this.currentPageNum--;
		if( this.currentPageNum < 0 )
			this.currentPageNum = 0;
		return getSpecificationPageBeanList(this.currentPageNum);
	}

	/**
	 * 次のページを取得する。
	 * 改ページ無しの時は使用しない。
	 * 改ページありのときは現在のページの次のページを返す。
	 * @return List
	 * @throws SQLException
	 */
	public List getNextPageBeanList() throws SQLException
	{
		this.currentPageNum++;
		if( currentPageNum != 0 && currentPageNum >= ((int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1) )
			currentPageNum = (int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1;
		return getSpecificationPageBeanList(this.currentPageNum);
	}

	/**
	 * 最後のページを返す。
	 * 改ページ無しの時は使用しない。
	 * 改ページありのときは最後のページを返す。
	 * @return List
	 * @throws SQLException
	 */
	public List getLastPageBeanList() throws SQLException
	{
		this.currentPageNum = (int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1;
		return getSpecificationPageBeanList(this.currentPageNum);
	}

	/**
	 * 指定ページを返す。
	 * 改ページ無しの時は使用しない。
	 * 改ページありのときは指定したページを取得する。
	 * @param pageNumber ページ番号
	 * @return List
	 * @throws SQLException
	 */
	public List getSpecificationPageBeanList(int pageNumber) throws SQLException
	{
		this.currentPageNum = pageNumber;
		if( rowsInPage == 0 )
		{
			currentBeanList = dataModule.getBean();
			maxRows = dataModule.getMaxRows();
		}
		else
		if( !cached )
		{
			currentBeanList = dataModule.getBean( ((pageNumber) * rowsInPage), ((pageNumber + 1) * rowsInPage) );
			maxRows = dataModule.getMaxRows();
			if( (maxRows - 1) < pageNumber * rowsInPage )
			{
				pageNumber = (maxRows - 1) / rowsInPage;
				currentPageNum = pageNumber;
				currentBeanList = dataModule.getBean( ((pageNumber) * rowsInPage), ((pageNumber + 1) * rowsInPage) );
			}
		}
		else
		{
			if( cacheBeanList == null )
			{
				cacheBeanList = dataModule.getBean();
				maxRows = dataModule.getMaxRows();
			}
			else
			{
				maxRows = cacheBeanList.size();
			}
			if( (maxRows - 1) < pageNumber * rowsInPage )
			{
				pageNumber = (maxRows - 1) / rowsInPage;
				currentPageNum = pageNumber;
			}
			Iterator cacheIte = cacheBeanList.iterator();
			currentBeanList = new ArrayList();
			int rows = 0;
			for( ; rows < ( pageNumber * rowsInPage) && cacheIte.hasNext() ; cacheIte.next(),rows++ );
			for( ; rows < ( (pageNumber + 1) * rowsInPage) && cacheIte.hasNext() ;rows++ )
			{
				currentBeanList.add( cacheIte.next() );
			}
		}
		return currentBeanList;
	}

	/**
	 * 現在のページ番号を返す。
	 * @return int
	 */
	public int getCurrentPageNumber()
	{
		return currentPageNum + 1;
	}

	/**
	 * 最後のページ番号を返す。
	 * @return
	 */
	public int getLastPageNumber()
	{
		if( rowsInPage == 0 )
			return 0;
		return (int)Math.ceil( (double)maxRows / (double)rowsInPage );
	}

	/**
	 * 現在のページの１行のが全体の何行目かを返す
	 * @return int
	 */
	public int getStartRowInPage()
	{
		// バグの対応(データが０件の時に１-１０件を０-０件にする) 20020812 yoshi
		if( this.maxRows == 0 )
			return 0;
		return rowsInPage * currentPageNum + 1;
	}

	/**
	 * 現在のページの最後の行が全体の何行目かを返す。
	 * @return int
	 */
	public int getEndRowInPage()
	{
		// バグの対応(データが０件の時に１-１０件を０-０件にする) 20020812 yoshi
		if( this.maxRows == 0 )
			return 0;
		if( currentPageNum == ((int)Math.ceil( (double)maxRows / (double)rowsInPage ) - 1))
			return maxRows;
		return (currentPageNum + 1) * rowsInPage;
	}

	/**
	 * 取得した件数を返す。
	 * @return int
	 */
	public int getMaxRows()
	{
		return this.maxRows;
	}
	
	/**
	 * 件数をセット。
	 * @return int
	 */
	public void setMaxRows( int maxRows )
	{
		this.maxRows = maxRows;
		return;
	}

	/**
	 * 与えられたList内のBeanをＤＢから削除する。
	 * @param beanList
	 * @return boolean 成功したか、失敗したかを返す。
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean deleteBeans(List beanList)
	{
		Iterator ite = beanList.iterator();
		while( ite.hasNext() )
			if( !dataModule.deleteBean( ite.next() ) )
				return false;
		return true;
	}

	/**
	 * 与えられたList内のBeanをＤＢに挿入する。
	 * @param beanList
	 * @return boolean 成功したか、失敗したかを返す。
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean insertBeans(List beanList)
	{
		Iterator ite = beanList.iterator();
		while( ite.hasNext() )
			if( !dataModule.insertBean( ite.next() ) )
				return false;
		return true;
	}

	/**
	 * 与えられたList内のBeanをＤＢに更新する。
	 * @param beanList
	 * @return boolean 成功したか、失敗したかを返す。
	 * @deprecated このメソッドはトランザクションとしては処理出来ないため、DataBaseクラスに直接SQLを投げる方が望ましい。
	 */
	public boolean updateBeans(List beanList)
	{
		Iterator ite = beanList.iterator();
		while( ite.hasNext() )
			if( !dataModule.updateBean( ite.next() ) )
				return false;
		return true;
	}

	/**
	 * ＤＢ接続を開放する。
	 */
	public void close()
	{
		dataModule.close();
	}

	/**
	 * 更新や削除を反映する。
	 */
	public void commit()
	{
		dataModule.commit();
	}

	/**
	 * 更新や削除の反映をクリアする。
	 */
	public void rollback()
	{
		dataModule.rollback();
	}
}
