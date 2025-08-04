package mdware.common.batch.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import jp.co.vinculumjapan.stc.util.db.DataModule;

/**
 * <p>タイトル：ヘッダ・明細・トレーラデータ保持クラス</p>
 * <p>説明：データ交換に使用されるヘッダ・明細・トレーラのBeanを</p>
 * <p>　　　１セットにして保持します </p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/15 kaneda 新規作成
 */
public abstract class AbstractBeanSet {
	
	private Object header = null;
	private ArrayList bodyList = new ArrayList();
	private Object trailer = null;

	private DataModule headerDM = null;
	private DataModule bodyDM = null;
	private DataModule trailerDM = null;
	
	
	/**
	 * コンストラクタ
	 */
	public AbstractBeanSet(){
	}
	
	/**
	 * ヘッダデータを設定します
	 * @param bean ヘッダBean
	 */
	public void setHeaderBean(Object bean){
		this.header = bean;
	}
	
	/**
	 * ヘッダデータを取得します
	 * @return ヘッダBean
	 */
	public Object getHeaderBean(){
		return this.header;
	}
	
	/**
	 * ヘッダデータに対して、明細データが妥当であるか検証します
	 * @param bean 明細Bean
	 * @return 検証結果
	 */
	protected abstract boolean equalsKeyBody(Object bean);
	
	/**
	 * 明細データを追加します
	 * @param bean 明細Bean
	 */
	public void addBodyBean(Object bean){
		this.bodyList.add(bean);
	}

	/**
	 * 明細データを取得します
	 * @param index 返される要素のインデックス 
	 * @return 明細Bean
	 */
	public Object getBodyBean(int index){
		return this.bodyList.get(index);
	}
	
	/**
	 * 明細データリストを取得します
	 * @return 明細Beanリスト
	 */
	public List getBodyBeanList(){
		return this.bodyList;
	}

	/**
	 * 明細数を取得します
	 * @return 明細Bean数
	 */
	public int getBodySize(){
		return this.bodyList.size();
	}
	
	/**
	 * ヘッダデータに対して、トレーラが妥当であるか検証します
	 * @param bean トレーラBean
	 * @return 検証結果
	 */
	protected abstract boolean equalsKeyTrailer(Object bean);

	/**
	 * トレーラデータを設定します
	 * @param bean トレーラBean
	 */
	public void setTrailerBean(Object bean){
		this.trailer = bean;
	}
	
	/**
	 * トレーラデータを取得します
	 * @return トレーラBean
	 */
	public Object getTrailerBean(){
		return this.trailer;
	}
	
	/**
	 * ヘッダINSERT用SQLを取得します
	 * @return ヘッダINSERT用SQL
	 * @throws Exception
	 */
	public String getHeaderInsertSQL(Object bean) throws Exception{
		if(this.headerDM == null){
			String dmName = this.getHeaderDMName();
			if(dmName != null){
				this.headerDM = (DataModule)Class.forName(dmName).newInstance();
				headerDM.close();
			}else{
				throw new Exception("SQLの作成に失敗しました。ヘッダ用のDataModule名を確認してください。");		
			}
		}
		if(bean == null){
			throw new Exception("引数のObjectがNullです。");
		}
		return this.headerDM.getInsertSql(bean);
	}
	
	/**
	 * ヘッダDataModule名を取得します
	 * @return ヘッダDataModule名
	 */
	public abstract String getHeaderDMName();
	
	
	/**
	 * 明細INSERT用SQLを取得します
	 * @return 明細INSERT用SQL
	 * @throws Exception
	 */
	public String getBodyInsertSQL(Object bean) throws Exception{
		if(this.bodyDM == null){
			String dmName = this.getBodyDMName();
			if(dmName != null){
				this.bodyDM = (DataModule)Class.forName(dmName).newInstance();
				bodyDM.close();
			}else{
				throw new Exception("SQLの作成に失敗しました。明細用のDataModule名を確認してください。");		
			}
		}
		if(bean == null){
			throw new Exception("引数のObjectがNullです。");
		}
		return this.bodyDM.getInsertSql(bean);
	}
	
	/**
	 * 明細DataModule名を取得します
	 * @return 明細DataModule名
	 */
	public abstract String getBodyDMName();

	/**
	 * トレーラINSERT用SQLを取得します
	 * @return トレーラINSERT用SQL
	 * @throws Exception
	 */
	public String getTrailerInsertSQL(Object bean) throws Exception{
		if(this.trailerDM == null){
			String dmName = this.getTrailerDMName();
			if(dmName != null){
				this.trailerDM = (DataModule)Class.forName(dmName).newInstance();
				trailerDM.close();
			}else{
				throw new Exception("SQLの作成に失敗しました。トレーラ用のDataModule名を確認してください。");		
			}
		}
		if(bean == null){
			throw new Exception("引数のObjectがNullです。");
		}
		return this.trailerDM.getInsertSql(bean);
	}
	
	/**
	 * トレーラDataModule名を取得します
	 * @return トレーラDataModule名
	 */
	public abstract String getTrailerDMName();
	
}	
