package mdware.common.util;

import java.util.*;
import java.sql.*;

import jp.co.vinculumjapan.stc.util.db.DataBase;
import jp.co.vinculumjapan.stc.log.StcLog;

import mdware.common.bean.DenpyoNbControlBean;

/**
 * <p>タイトル: 伝票番号生成</p>
 * <p>説明:伝票採番を行います。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author shimatani
 * @version 1.00 2004/8/13 shimatani 新規作成
 * @version 1.10 2004/11/15 変更依頼No071 Shimatani
 * @version 1.11 2004/11/15 障害報告書No456 Shimatani
 * @version 1.20 2005/01/06 takata
 */
public class ControlDenpyoNb {

	private static CheckDigitCalculator checkDigitCalculator = new PosfulCheckDigitCalculator(); 

	/**
	 * 連番の取得を行う。
	 * @param data_syubetu  伝票種別
	 * @param rb_syubetu	rbsite内での種別
	 * @return 伝票番号
	 * @throws SQLException　
	 * @throws Exception
	 */
	public static synchronized String getDenpyoNb(String data_syubetu, String rb_syubetu) throws Exception {
		return getDenpyoNb(new DataBase("rbsite_ora"), data_syubetu, rb_syubetu);
	}

	/**
	 * 連番の取得を行う。
	 * @param DataBase  使用するコネクションを指定することができる（主にコネクションプールを使用しないバッチ処理時に使用する）
	 * @param data_syubetu  伝票種別
	 * @param rb_syubetu	rbsite内での種別
	 * @return 伝票番号
	 * @throws SQLException　
	 * @throws Exception
	 */
	public static synchronized String getDenpyoNb(DataBase database, String data_syubetu, String rb_syubetu) throws Exception {

		String nextVal = "0"; // 新しい連番
		ResultSet resultSet = null;	
		
		try{
			
			StringBuffer sb = new StringBuffer("");

			// 暗黙のトランザクション開始
			database.commit();

			sb.append("select ");
			sb.append("    denpyo_syubetu_kb ");
			sb.append("    ,rb_saiban_syubetu_kb ");
			sb.append("    ,now_nb ");
			sb.append("    ,case when now_nb + 1 > to_nb then from_nb ");
			sb.append("          else to_char(to_number(now_nb) + 1) ");
			sb.append("     end as before_nb ");
			sb.append("    ,length(rtrim(now_nb)) as denpyo_len ");
			sb.append("from ");
			sb.append("    ma_denpyo_control ");
			sb.append("where ");
			sb.append("    denpyo_syubetu_kb = '" + data_syubetu + "' ");
			sb.append("    and rb_saiban_syubetu_kb = '" + rb_syubetu + "' ");
			sb.append("for update ");
			
			resultSet = database.executeQuery(sb.toString());
			
			if (!resultSet.next()) {
				// 取得できなかった場合
				StcLog.getInstance().getLog().fatal("連番名称がありません。名称:" + rb_syubetu);
				try{resultSet.close();}catch(SQLException sqle){};
				database.close();
				database = null;
				resultSet = null;
				throw new Exception();			
			}

			DenpyoNbControlBean denpyoNbControlBean = new DenpyoNbControlBean(resultSet);
			
			nextVal = StringUtility.adjustStringWithZero(denpyoNbControlBean.getNowNb().trim(), denpyoNbControlBean.getDenpyoLen());
			int cnt = 0;
			// 新しい連番の更新
			sb = new StringBuffer("");
			sb.append("update ma_denpyo_control ");
			sb.append("set ");
			sb.append("    now_nb = '" + StringUtility.adjustStringWithZero(denpyoNbControlBean.getBeforeNb().trim(), denpyoNbControlBean.getDenpyoLen()) + "' ");
			sb.append("where ");
			sb.append("    denpyo_syubetu_kb = '" + denpyoNbControlBean.getDenpyoSyubetuKb() + "' ");
			sb.append("    and rb_saiban_syubetu_kb = '" + denpyoNbControlBean.getRbSaibanSyubetuKb() + "' ");
			
			// 更新が成功した場合はcommit、失敗した場合はrollbackする
			if(database.executeUpdate(sb.toString()) == 1) {
				database.commit();
			}
			else {
				database.rollback();
				StcLog.getInstance().getLog().fatal("連番の更新に失敗しました。");
				try{resultSet.close();}catch(SQLException sqle){};
				database.close();
				database = null;
				resultSet = null;
				throw new Exception();			
			}

		} catch (Exception e) {
			StcLog.getInstance().getLog().fatal("連番の取得に失敗しました。");
			try{resultSet.close();}catch(SQLException sqle){};
			database.close();
			database = null;
			resultSet = null;
			throw e;
			
		} finally {
			try{resultSet.close();}catch(SQLException sqle){};
			database.close();
			database = null;
			resultSet = null;
		}
		
		// チェックディジットを付加して伝票番号を返す。
		return nextVal + checkDigitCalculator.calculate(nextVal);
	}

	public static void main(String[] args) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";

		mdware.common.batch.util.control.BatchController controller =
			new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);
		try {
			System.out.println(ControlDenpyoNb.getDenpyoNb("04", "eos"));
		} catch (Exception ex) {
			System.out.println("err");
		}
	}

}
