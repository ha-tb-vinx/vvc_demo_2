package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.util.system.MDWareSystemControlUtil;
import mdware.master.properties.MasterCommonPropertiesManager;
import mdware.master.properties.MasterCommonPropertiesParameter;

/**
 * <P>タイトル : mst160101_SyohinIkkatuMenteLogicBeanクラス</P>
 * <P>説明 : 新ＭＤシステム  商品マスタ一括修正（条件指定）画面のロジックを(一部)実装したクラスです</P>
 * <P>著作権: Copyright (c) 2006</p>								
 * <P>会社名: Vinculum Japan Corp.</P>			
 * @author K.Tanigawa
 * @version 1.0 (2006.11.07)初版作成
 * @see なし								
 */
public class mst160101_SyohinIkkatuMenteLogicBean {

	public final static String PROC_MAX_NUM = "3000";

	/***
	 * 有効日取得　＋　
	 * 処理対象件数が、本日の上限をオーバーする場合は、翌日に回す。
	 * 翌日にも処理できない場合は、翌々日へと回す…という作業を処理できる日付を発見できるまで行う。
	 */
	public void setYukoDtAndTorokuTs(mst160201_KeepBean Keepb) throws SQLException{

		mst870201_MstDateBean mstDateBean = mst870201_MstDateBean.getBean(new DataHolder());
		Keepb.setYukoDt(mstDateBean.getMstDateDtNext());			//有効日
		Keepb.setDefaultYukoDt(Keepb.getYukoDt());					//デフォルト有効日
		
		this.setTorokuTs(Keepb);
	}


	/***
	 * 処理対象件数が、本日の上限をオーバーする場合は、翌日に回す。
	 * 翌日にも処理できない場合は、翌々日へと回す…という作業を処理できる日付を発見できるまで行う。
	 */
	public void setTorokuTs(mst160201_KeepBean Keepb) throws SQLException{

		//商品一括メンテデータの取り込みバッチが低速であるため、
		//バッチが処理できる件数をmaster_common.propertiesファイルから取得する。
		//(データがなかったり、数値以外が登録されている場合は、初期値3000を入れる)
		MasterCommonPropertiesManager mcpManager = MasterCommonPropertiesManager.getInstance();
		String strProcMaxNum = mcpManager.getParameter(MasterCommonPropertiesParameter.SYOHIN_MST_ALL_MODIFY_SCREEN_PROC_MAX);
		if( strProcMaxNum == null || !strProcMaxNum.matches("^[0-9]+$") ){
			strProcMaxNum = mst160101_SyohinIkkatuMenteLogicBean.PROC_MAX_NUM;
		}

		BeanHolder bh = new BeanHolder(new RSyohinIkkatuMenteCountDM());
		bh.setDataHolder(new DataHolder());
		List l = bh.getFirstPageBeanList();
		Keepb.setTorokuTs(null);
		boolean bTourokuTsExists = false;

		if( !l.isEmpty() ){
			for( Iterator ite = l.iterator(); ite.hasNext(); ){
				RSyohinIkkatuMenteCountBean rsimcBean = (RSyohinIkkatuMenteCountBean)ite.next();

				//１処理のデータ量を１日分のデータとして登録するため、
				//(チェックを入れた件数 + DB上に既に登録されている件数) >= 処理対象上限件数の場合は、
				//その日に値を入れず、次の日の確認に進む。
				if( ((long)Keepb.getProcNum() + (long)rsimcBean.getRegisteredNum()) >= (long)Integer.parseInt(strProcMaxNum) ){
					continue;
				}
				
				bTourokuTsExists = true;
				Keepb.setTorokuTs(rsimcBean.getTourokuTs().trim());
				break;
			}
		}
		//DBに登録されている登録年月日内のデータに空きがない場合は、
		//MAX(登録年月日) + 1日を取得し、その日付を登録年月日とする
		if( !bTourokuTsExists  && !l.isEmpty() ){
			BeanHolder tmpBh = new BeanHolder(new RSyohinIkkatuMenteNextDayDM());
			List tmpList = tmpBh.getFirstPageBeanList();
			RSyohinIkkatuMenteNextDayBean rsimndBean = (RSyohinIkkatuMenteNextDayBean)tmpList.get(0);
			Keepb.setTorokuTs(rsimndBean.getNextday());
		}else if ( !bTourokuTsExists  && l.isEmpty() ){
			Keepb.setTorokuTs(MDWareSystemControlUtil.getMstOnlineDate());
		}

		//処理しようとしている件数が、処理対象上限件数を越えている場合は、画面でエラーとして表示する(アラートメッセージを表示する)
		//1回○件以上は受け付けることはできません。
		Keepb.setProcMaxNum(Integer.parseInt(strProcMaxNum));
	}

}
