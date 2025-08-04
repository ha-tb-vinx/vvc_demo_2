package mdware.common.batch.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.util.Map;


/**
 * <p>タイトル：AbstractBeanSetリストクラス</p>
 * <p>説明：AbstractBeanSetをリストとして保持するクラスです</p>
 * <p>著作権：Copyright (c) 2004</p>
 * <p>会社名：Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/14 kaneda 新規作成
 */
public class BeanList{
	
	private ArrayList beanSetList = new ArrayList();
	
	/**
	 * AbstractBeanSetを追加します
	 * @param beanSet AbstractBeanSetクラス
	 */
	public void addBeanSet(AbstractBeanSet beanSet){
		this.beanSetList.add(beanSet);
	}
	
	/**
	 * AbstractBeanSetを取得します
	 * @param index 返される要素のインデックス 
	 */
	public AbstractBeanSet getBeanSet(int index){
		return (AbstractBeanSet)this.beanSetList.get(index);
	}

	/**
	 * 保持されているAbstractBeanSet数を取得します
	 * @return AbstractBeanSet数
	 */
	public int getBeanSetSize(){
		return this.beanSetList.size();
	}

	///////////////////////////////////////////////////////////////////////////////////
		
	/**
	 * 保持されているAbstractBeanSetに明細データを設定します<BR>
	 * AbstractBeanSetのヘッダデータに一致する明細データを検索し<BR>
	 * 一致するヘッダデータをもつAbstractBeanSetに明細データを設定します
	 * @param bean 明細Bean
	 * @return 処理結果（true:設定完了　false:未設定）
	 */
	public boolean addBodyBean(Object bean){
		for(int i = 0; i < this.beanSetList.size(); i++){
			AbstractBeanSet abs = (AbstractBeanSet)this.beanSetList.get(i);
			if(abs.equalsKeyBody(bean)){
				abs.addBodyBean(bean);
				return true;
			}
		}
		return false;
	}

	/**
	 * 保持されているAbstractBeanSetにトレーラを設定します<BR>
	 * AbstractBeanSetのヘッダデータに一致するトレーラデータを検索し<BR>
	 * 一致するヘッダデータをもつAbstractBeanSetにトレーラデータを設定します
	 * @param bean トレーラBean
	 * @return 処理結果（true:設定完了　false:未設定）
	 */
	public boolean addTrailerBean(Object bean){
		for(int i = 0; i < this.beanSetList.size(); i++){
			AbstractBeanSet abs = (AbstractBeanSet)this.beanSetList.get(i);
			if(abs.equalsKeyTrailer(bean)){
				abs.setTrailerBean(bean);
				return true;
			}
		}
		return false;
	}

	/**
	 * 保持されているAbstractBeanSetのリストからヘッダデータを取得します
	 * @return ヘッダBeanリスト
	 */
	public List getHeaderBeanList(){
		ArrayList headerBeanList = new ArrayList();
		for(int i = 0; i < this.beanSetList.size(); i++){
			headerBeanList.add(((AbstractBeanSet)this.beanSetList.get(i)).getHeaderBean());
		}
		return headerBeanList;
	}

	/**
	 * 保持されているAbstractBeanSetのリストから明細データを取得します
	 * @return 明細Beanリスト
	 */
	public List getBodyBeanList(){
		ArrayList bodyBeanList = new ArrayList();
		for(int i = 0; i < this.beanSetList.size(); i++){
			List list = ((AbstractBeanSet)this.beanSetList.get(i)).getBodyBeanList();
			if(list.size() > 0){
				bodyBeanList.addAll(list);
			}
		}
		return bodyBeanList;
	}

	/**
	 * 保持されているAbstractBeanSetのリストからトレーラデータを取得します
	 * @return トレーラBeanリスト
	 */
	public List getTrailerBeanList(){
		ArrayList trailerBeanList = new ArrayList();
		for(int i = 0; i < this.beanSetList.size(); i++){
			Object bean = ((AbstractBeanSet)this.beanSetList.get(i)).getTrailerBean();
			if(bean != null){
				trailerBeanList.add(bean);
			}
		}
		return trailerBeanList;
	}
}
