package mdware.common.batch.util.convert;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mdware.common.batch.bean.AbstractBeanSet;
import mdware.common.batch.bean.BeanList;
import mdware.common.batch.log.BatchLog;
import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.util.db.DataModule;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル：テーブル・Bean間データ交換クラス</p>
 * <p>説明：テーブルのデータのBean化や、Beanのデータをテーブルに登録処理を</p>
 * <p>　　　SQLを意識せずに行うことができます</p>
 * <p>著作権： Copyright (c) 2004</p>
 * <p>会社名： Vinculum Japan Corp.</p>
 * @author kaneda
 * @version 1.00 2004/07/16 kaneda 新規作成
 */
public class BeanListExchanger {

	/**
	 * テーブルから取得したデータをBeanのリストにして返します<BR>
	 * @param dm テーブルに対応したDataModule
	 * @param query テーブルの検索キー
	 * @return 検索されたBeanのリスト
	 * @throws SQLException
	 */
	public static List createBeanList(DataModule dm, Map query) throws SQLException{
		DataHolder dh = new DataHolder();
		Iterator ite = query.keySet().iterator();
		while(ite.hasNext()){
			String key = ite.next().toString();
			dh.updateParameterValue(key, query.get(key).toString());
		}
		dm.setDataHolder(dh);
		List list = null;
		try{
			list = dm.getBean();
			return list;
		}catch(SQLException e){
			throw e;
		}finally{
			dm.close();
		}
	}

	/**
	 * BeanListのBeanSetを、ヘッダ・明細・トレーラテーブルに設定します<BR>
	 * BeanSetのインサート毎にcommit処理をします<BR>
	 * @param db DBアクセサ
	 * @param beanList BeanList
	 * @return ヘッダ処理件数
	 * @throws Exception
	 */
	public static int modifyBeanList(DataBase db, BeanList beanList) throws Exception{
		int resultCount = 0;
		String sql = "";
		try {
			for(int i = 0; i < beanList.getBeanSetSize(); i++){
				AbstractBeanSet abs = beanList.getBeanSet(i);
				sql = abs.getHeaderInsertSQL(abs.getHeaderBean());
				db.executeUpdate(sql);
				for(int j = 0; j < abs.getBodySize(); j++){
					sql = abs.getBodyInsertSQL(abs.getBodyBean(j));
					resultCount += db.executeUpdate(sql);
				}
				Object trailerBean = abs.getTrailerBean();
				if(trailerBean != null){
					sql = abs.getTrailerInsertSQL(trailerBean);
					db.executeUpdate(sql);
				}
				db.commit();
			}
		} catch (SQLException e) {
			String errorMsg = "";
			errorMsg = "SQL処理中（INSERT）にエラーが発生しました。エラーコード[" + e.getErrorCode() + "] SQL文" + sql;
			throw new Exception(errorMsg, e);
		} catch (Exception e) {
			throw e;
		}
		return resultCount;
	}
	
	/**
	 * BeanのListを、DataModuleを使用してテーブルに一括INSERTします<BR>
	 * 処理途中にエラーが発生した場合は、処理を中断してRollBackをします<BR>
	 * @param dm DataModule
	 * @param list Beanのリスト
	 * @return 処理結果（true：成功　false：失敗）
	 */
	public static boolean modifyBean(DataModule dm, List list) throws Exception{
		try{
			for(int i = 0; i < list.size(); i++){
				if(!dm.insertBean(list.get(i))){
					dm.rollback();
					return false;
				}
			}
			dm.commit();
			return true;
		}finally{
			dm.close();
		}
	}
	
	/**
	 * Beanを、DataModuleを使用してテーブルにINSERTします<BR>
	 * 処理途中にエラーが発生した場合は、処理を中断してRollBackをします<BR>
	 * @param dm DataModule
	 * @param obj Bean
	 * @return 処理結果（true：成功　false：失敗）
	 */
	public static boolean modifyBean(DataModule dm, Object obj) throws Exception{
		try{
			if(dm.insertBean(obj)){
				dm.commit();
				return true;
			}else{
				dm.rollback();
				return false;
			}
		}finally{
			dm.close();
		}
	}
}
