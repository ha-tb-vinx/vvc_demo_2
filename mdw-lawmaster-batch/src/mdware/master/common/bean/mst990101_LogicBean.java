package mdware.master.common.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import mdware.common.util.StringUtility;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.common.dictionary.mst000901_KanriKbDictionary;
import mdware.master.common.dictionary.mst003901_YotoKbDictionary;

import org.apache.log4j.Category;

/**
 * <p>タイトル: メーカーコードロジックBean</p>
 * <p>説明: メーカーコード照会画面で使用する主なロジックをまとめたクラスです。Beanなのに、ロジックが入るのは…。</p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @author k_tanigawa
 * @version 1.0 (2006/10/15) 初版作成
 * @version 1.1 (2006.10.17) 爆速ver. K.Tanigawa
 */
public class mst990101_LogicBean {

	/****************************************************
	 ****************************************************
	 ****************************************************
	                       変数宣言
	 ****************************************************
	 ****************************************************
	 ****************************************************/

	private Category logger = null;
	
	
	/****************************************************
	 ****************************************************
	 ****************************************************
	                    コンストラクタ
	 ****************************************************
	 ****************************************************
	 ****************************************************/
	public mst990101_LogicBean(Category logger){
		this.logger = logger;
	}


	/****************************************************
	 ****************************************************
	 ****************************************************
                    　　 メソッド宣言
	 ****************************************************
	 ****************************************************
	 ****************************************************/

	/**
	 * 部門名称を取得する
	 * @param  String bumon_cd 部門コード
	 * @return String 部門コードが存在すれば部門名称
	 *                 なければnull
	 * @exception SQLException
	 */
	public String getBumonNa(String bumon_cd) throws SQLException{

		BeanHolder bh = new BeanHolder(new mst000301_NameCtfDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("syubetu_no_cd", mst000101_ConstDictionary.BUNRUI1);
		dh.updateParameterValue("delete_fg",mst000801_DelFlagDictionary.INAI.getCode());
		dh.updateParameterValue("code_cd", StringUtility.charFormat(bumon_cd,4,"0",true).trim());
		bh.setDataHolder(dh);

		List l = bh.getFirstPageBeanList();
		if( l.isEmpty() ){
			return null;
		}
		mst000301_NameCtfBean bean = (mst000301_NameCtfBean)l.get(0);

		return ( bean.getKanjiRn() == null ? "" : bean.getKanjiRn().trim() );
	}
	
	/**
	 * 仕入先名称を取得する
	 * @param  String siiresaki_cd 仕入先コード
	 * @return String 仕入先コードが存在すれば仕入先名称
	 *                 なければnull
	 * @exception SQLException
	 */
	public String getSiiresakiNa(String siiresaki_cd) throws SQLException{

		//仕入先コードは必須入力項目ではないので、
		//NULLか空文字を引数として受け取った場合は、空文字を返す
		if( siiresaki_cd == null || siiresaki_cd.trim().length() <= 0 ){
			return "";
		}
		
		BeanHolder bh = new BeanHolder(new mst440401_RSiiresakiDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("kanri_kb", mst000901_KanriKbDictionary.BUMON.getCode());
		dh.updateParameterValue("kanri_cd", "0000");
		dh.updateParameterValue("siiresaki_cd_aft_like", siiresaki_cd);
		bh.setDataHolder(dh);
		
		List l = bh.getFirstPageBeanList();
		if( l.isEmpty() ){
			return null;
		}
		mst440401_RSiiresakiBean bean = (mst440401_RSiiresakiBean)l.get(l.size() - 1);	//一番値の大きい仕入先コードの名称を取得する(前方一致検索だと、複数ヒットする可能性があるため)

		return ( bean.getKanjiRn() == null ? "" : bean.getKanjiRn().trim() );
	}

	/**
	 * メーカー名称を取得する
	 * @param  String maker_cd メーカーコード
	 * @return String メーカーコードが存在する場合は、メーカー名称
	 *                 メーカーコードとしてNULLか空文字がきた場合は、""(空文字)を返す
	 *                 なければnull
	 * @exception SQLException
	 */
	public String getMakerNa(String maker_cd) throws SQLException{

		//メーカーコードは必須入力項目ではないので、
		//NULLか空文字を引数として受け取った場合は、空文字を返す
		if( maker_cd == null || maker_cd.trim().length() <= 0 ){
			return "";
		}
		
		BeanHolder bh = new BeanHolder(new mst000301_NameCtfDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("syubetu_no_cd", mst000101_ConstDictionary.MAKER_NAME);
		dh.updateParameterValue("code_cd", maker_cd);
		bh.setDataHolder(dh);

		List l = bh.getFirstPageBeanList();
		if( l.isEmpty() ){
			return null;
		}
		mst000301_NameCtfBean bean = (mst000301_NameCtfBean)l.get(0);
		
		return ( bean.getKanjiRn() == null ? "" : bean.getKanjiRn().trim() );
	}
	
	/**
	 * 店配列名称を取得する
	 * @param  String tenHairetuCd 店配列コード
	 * @return String 店配列コードが存在すれば、店配列名称
	 *                 なければnull
	 * @exception SQLException
	 */
	public String getGroupNoNa(String bumon_cd, String groupno_cd) throws SQLException{

		BeanHolder bh = new BeanHolder(new mst580101_TenGroupNoDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("bumon_cd", StringUtility.charFormat(bumon_cd,4,"0",true));
		dh.updateParameterValue("yoto_kb", mst003901_YotoKbDictionary.HACHU.getCode());
		dh.updateParameterValue("groupno_cd", groupno_cd);
		bh.setDataHolder(dh);

		List l = bh.getFirstPageBeanList();
		if( l.isEmpty() ){
			return null;
		}
		mst580101_TenGroupNoBean bean = (mst580101_TenGroupNoBean)l.get(0);
		
		return ( bean.getNameNa() == null ? "" : bean.getNameNa().trim() );
	}

	/**
	 * 検索処理を実施する
	 * @param  mst990101_KeepBean gamenInfo 画面情報保持Bean
	 * @return List 検索結果があればそのList　なければNULL
	 * @exception SQLException
	 */
	public List searchAllTenpo() throws SQLException {

		BeanHolder bh = new BeanHolder(new mst990101_AllTenpoDM());
		DataHolder dh = new DataHolder();
		bh.setDataHolder(dh);
		
		List l = bh.getFirstPageBeanList();
		
		return l;
	}

	/**
	 * 検索処理を実施する
	 * @param  mst990101_KeepBean gamenInfo 画面情報保持Bean
	 * @return List 検索結果があればそのList　なければNULL
	 * @exception SQLException
	 */
	public List searchTenpo(String bumon_cd, String groupno_cd) throws SQLException {

		BeanHolder bh = new BeanHolder(new mst990101_RTenGroupDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("bumon_cd", StringUtility.charFormat(bumon_cd,4,"0",true));
		dh.updateParameterValue("groupno_cd", groupno_cd);
		bh.setDataHolder(dh);

		List l = bh.getFirstPageBeanList();
		
		return l;
	}

	/**
	 * 検索処理を実施する
	 * @param  mst990101_KeepBean gamenInfo 画面情報保持Bean
	 * @return List 検索結果があればそのList　なければNULL
	 * @exception SQLException
	 */
	public BeanHolder search(mst990101_KeepBean gamenInfo, List searchAllList, List tenpoList, int displayRows) throws SQLException{
		
		BeanHolder bh = new BeanHolder(new mst990101_MakerCdTenbetuDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("bumon_cd", StringUtility.charFormat(gamenInfo.getBumonCd(),4,"0",true));
		dh.updateParameterValue("groupno_cd", gamenInfo.getGroupNoCd());
		dh.updateParameterValue("maker_cd", gamenInfo.getMakerCd());
		dh.updateParameterValue("siiresaki_cd", gamenInfo.getSiiresakiCd());
//		HashMap hm = dh.getParameterMap();	//DataHolderにListを無理矢理入れちゃう裏技
//		hm.put("tenpoList", tenpoList);
//		bh.setRowsInPage(displayRows);
		bh.setDataHolder(dh);

		this.logger.info("店別仕入先マスタの検索処理を開始します。");
		List dbl = bh.getFirstPageBeanList();
		if( dbl.isEmpty() ){
			return bh;
		}
		this.logger.info("店別仕入先マスタの検索処理を終了しました。");
		
		this.logger.info("店別仕入先マスタから取得したデータの整形処理を開始します。");
		List l = this.createTenbetuSiiresakiMstList(dbl, searchAllList, tenpoList);

		BeanHolder bhReturn = new BeanHolder();
		bhReturn.setRowsInPage(displayRows);
		bhReturn.setCache(true);
		bhReturn.setCacheBeanList(l);
		this.logger.info("店別仕入先マスタから取得したデータの整形処理を終了します。");
		
		return bhReturn;
	}
	

	/**
	 *  DBから取得した1行分のデータ( 店別仕入先マスタから取得した1行分のデータ(mst990101_MakerCdTenbetuBean) )には
	 *  メーカーコード(旧 店別仕入先管理コード)、仕入先コード、店舗コードが入っている。
	 *  店舗コードは、メーカーコード別、仕入先コード別に画面上で横一列に表示する必要があるため、
	 *  メーカーコード＋仕入先コードの単位で、店舗コードを取得してListに格納(tenpoList)→1行Bean(oneRowBean)に追加→1行Beanを全画面情報を保持するList(tenbetuSiiresakiMstList)に追加
	 *  という流れで画面上のデータを作成する。
	 *  
	 *  画面上には、ヘッダ店舗(店配列にひもづく店舗の名称を横展開したもの)が存在する。
	 *  ヘッダ店舗コードに存在して、店別仕入先マスタから取得したデータに存在しない 店舗の組み合わせロジックは、createTenbetuSiireakiMstWithAllTenpoListメソッドに記述している。
	 *  
	 *  最終的に、全画面情報を保持するListを返す。
	 * 
	 * @param  mst990101_KeepBean gamenInfo 画面情報保持Bean
	 * @return List 検索結果の整形後のList
	 * @exception SQLException
	 * @see mst990101_LogicBean.search()
	 * @see mst990101_LogicBean.createTenbetuSiireakiMstWithAllTenpoList()
	 */
	private List createTenbetuSiiresakiMstList(List dbl, final List searchAllList, final List tenpoList){

		List tenbetuSiiresakiMstList = new ArrayList();
		mst990101_MakerCdTenbetuBean preMakerBean = new mst990101_MakerCdTenbetuBean();
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		mst990101_MakerCdViewBean oneRowBean = new mst990101_MakerCdViewBean();	//画面上の1行分のデータを保持するBean
		mst990101_MakerCdViewBean oneRowBean = new mst990101_MakerCdViewBean(tenpoList);	//画面上の1行分のデータを保持するBean
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		for( Iterator ite = dbl.iterator(); ite.hasNext();){

			mst990101_MakerCdTenbetuBean makerBean = (mst990101_MakerCdTenbetuBean)ite.next();
			if(	( makerBean.getTenSiiresakiKanriCd().equals(preMakerBean.getTenSiiresakiKanriCd()) && makerBean.getSiiresakiCd().equals(preMakerBean.getSiiresakiCd()) ) ){
				preMakerBean = makerBean;
//				↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//				oneRowBean.setTenpoList( new mst990101_TenpoBean(makerBean) );
				oneRowBean.setTenpoList( makerBean );
//				↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
				continue;
			}

			this.logger.info("1明細分の整形処理を開始。" + makerBean.getTenSiiresakiKanriCd() + " - " + makerBean.getSiiresakiCd());
//			↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//			oneRowBean = new mst990101_MakerCdViewBean();
			oneRowBean = new mst990101_MakerCdViewBean(tenpoList);
//			↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
			oneRowBean.setTenpoCd(makerBean.getTenpoCd());
			oneRowBean.setTenSiiresakiKanriCd(makerBean.getTenSiiresakiKanriCd());
			oneRowBean.setTenSiiresakiKanriNa(makerBean.getTenSiiresakiKanriNa());
			oneRowBean.setSiiresakiCdWithTiku(makerBean.getSiiresakiCd());
			oneRowBean.setSiiresakiNa(makerBean.getSiiresakiNa());
			oneRowBean.setTenpoCountNum(makerBean.getTenpoCountNum());
//			↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//			oneRowBean.setTenpoList( new mst990101_TenpoBean(makerBean) );
			oneRowBean.setTenpoList( makerBean );
//			↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
			tenbetuSiiresakiMstList.add(oneRowBean);
			preMakerBean = makerBean;
		}
		
//		↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//		createTenbetuSiireakiMstWithAllTenpoList(tenbetuSiiresakiMstList, searchAllList, tenpoList);
//		↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		
		return tenbetuSiiresakiMstList;
	}

//	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * 全店舗、店配列と店別仕入先マスタの店舗を合わせ、画面上に表示する全データを1つのリストに格納する。
//	 * 
//	 * 「全店舗」の柱を基準に、店配列の店舗コードと店別仕入先マスタの店舗コードをその横に並べる。
//	 * 想定するパターンは以下の４つで、それぞれの説明は以下の通り。
//	 * 
//	 * パターン１：店配列、店別仕入先マスタの両方に店舗コードが存在する場合は、
//	 * 　　　　　　画面上に表示するチェックボックスをONの状態にしたBeanをListに格納する。
//	 * 
//	 * パターン２：店配列に店舗が「存在し」、店別仕入先マスタに店舗が「存在しない」場合は、
//	 * 　　　　　　画面上に表示するチェックボックスをOFFの状態にしたBeanをListに格納する。
//	 * 
//	 * パターン３：店配列に店舗が「存在せず」、店別仕入先マスタに店舗が「存在する」場合は、
//	 * 　　　　　　画面上には表示しない(ListにBeanを格納しない)。
//	 * 
//	 * パターン４：店配列、店別仕入先マスタの両方に店舗コードが存在しない場合は、
//	 * 　　　　　　画面上には表示しない(ListにBeanを格納しない)。
//	 * 　　　　　　パターン４は、ロジックを実装せずとも実現できるため、ＰＧ上のコードとしては存在しない。
//	 * 
//	 * 
//	 * 全店舗、店配列と店別仕入先マスタの店舗のデータと
//	 * 画面表示の関係を以下に示す。
//	 * 
//	 *                                店別仕入先マスタの   チェックボックス　　　 チェックボックス    
//	 * 全店舗　　　　　　店配列　　　　　　         店舗　　　　　 　の有無　　　　　　　 　の状態
//	 * 000101            000101            000101            　　有　　　            チェックあり      ・・・パターン１
//	 * 000102            000102                　            　　有　　　            チェックなし      ・・・パターン２
//	 * 000103                              000103                無                                    ・・・パターン３
//	 * 000104                                                    無                                    ・・・パターン４
//	 * 　・　　　　　　　　・　　　　　　　　・　　　　　　　　　・　　　　　　　　　　　・
//	 * 　・　　　　　　　　・　　　　　　　　・　　　　　　　　　・　　　　　　　　　　　・
//	 * 　・　　　　　　　　・　　　　　　　　・　　　　　　　　　・　　　　　　　　　　　・
//	 * 
//	 * @param	List 店別仕入先マスタの検索結果を格納したList(店別仕入先マスタに格納されている店舗)
//	 * @param	List 全店舗を格納したList(全店舗)
//	 * @param	List 画面上で選択した店配列にひもづく店舗コードを格納したList(ヘッダ店舗)
//	 * @return	List 整形後のList
//	 * @exception SQLException
//	 * @see mst990101_LogicBean.search()
//	 */
//	private List createTenbetuSiireakiMstWithAllTenpoList(final List tenbetuSiiresakiMstList, final List searchAllList,  final List tenpoList){
//		
//
//		for( Iterator ite = tenbetuSiiresakiMstList.iterator(); ite.hasNext(); ){
//
//			final mst990101_MakerCdViewBean oneRowBean = (mst990101_MakerCdViewBean)ite.next();
//			List currentTenpoList = oneRowBean.getTenpoList();
//			List newTenpoList = new ArrayList();
//
//			int iCurrentTenpoPointer = 0;
//			int iHeaderTenpoPointer  = 0;
//			int iMaxCurrentTenpoPointer = currentTenpoList.size() - 1;
//			int iMaxHeaderTenpoPointer = tenpoList.size() - 1;
//			for( Iterator ite2 = searchAllList.iterator(); ite2.hasNext(); ){
//
//				mst990101_AllTenpoBean allTenpoBean = (mst990101_AllTenpoBean)ite2.next();
//
//				//ヘッダ店舗数のカウンタが最大値を超えている場合は、処理を終了(ヘッダ店舗以上の店舗をListに入れる必要はないため)
//				if(	iMaxHeaderTenpoPointer < iHeaderTenpoPointer ) {
//					break;
//				}
//				
//				//1店別仕入先レコードの店舗のカウンタが最大値を超えている場合は、最大値を設定(1店別仕入先レコードの店舗以降の店舗に関しては、チェックボックスOFFの店舗がListに追加される)
//				if( iMaxCurrentTenpoPointer <= iCurrentTenpoPointer ){
//					iCurrentTenpoPointer = iMaxCurrentTenpoPointer;
//				}
//
//				//ヘッダ店舗、1店別仕入先レコードの店舗の「両方がある」場合は、
//				//店舗Listに店舗データ(チェックボックスON)を追加し、
//				//両ポインタを1つ移動
//				//パターン１
//				if(	
//					(allTenpoBean.getTenpoCd().equals(  ((mst990101_RTenGroupBean)tenpoList.get(iHeaderTenpoPointer)).getTenpoCd()  )) &&
//					(allTenpoBean.getTenpoCd().equals(  ((mst990101_TenpoBean)currentTenpoList.get(iCurrentTenpoPointer)).getTenpoCd()  ))
//				){
//						newTenpoList.add(currentTenpoList.get(iCurrentTenpoPointer));
//						iCurrentTenpoPointer = iCurrentTenpoPointer + 1;
//						iHeaderTenpoPointer  = iHeaderTenpoPointer  + 1;
//						continue;
//				}
//					
//				//ヘッダ店舗が「存在する」、1店別仕入先レコードの店舗が「存在しない」場合は、
//				//店舗Listに店舗データ(チェックボックスOFF)を追加し、
//				//ヘッダ店舗ポインタを1つ移動
//				//パターン２
//				if(
//					(allTenpoBean.getTenpoCd().equals(  ((mst990101_RTenGroupBean)tenpoList.get(iHeaderTenpoPointer)).getTenpoCd()  )) &&
//					!(allTenpoBean.getTenpoCd().equals(  ((mst990101_TenpoBean)currentTenpoList.get(iCurrentTenpoPointer)).getTenpoCd()  ))
//				){
//						mst990101_TenpoBean tenpoBean = new mst990101_TenpoBean();
//						tenpoBean.setTenpoCd(allTenpoBean.getTenpoCd());
//						tenpoBean.setRegistered(false);
//						newTenpoList.add(tenpoBean);
//						iHeaderTenpoPointer  = iHeaderTenpoPointer  + 1;
//						continue;
//				}
//					
//				//ヘッダ店舗が「存在しない」、1店別仕入先レコードの店舗が「存在する」場合は、
//				//1店別仕入先レコードの店舗ポインタを1つ移動(ヘッダ店舗はないので、店舗Listには追加しない)
//				//パターン３
//				if(
//					!(allTenpoBean.getTenpoCd().equals(  ((mst990101_RTenGroupBean)tenpoList.get(iHeaderTenpoPointer)).getTenpoCd()  )) &&
//					(allTenpoBean.getTenpoCd().equals(  ((mst990101_TenpoBean)currentTenpoList.get(iCurrentTenpoPointer)).getTenpoCd()  ))
//				){
//						iCurrentTenpoPointer = iCurrentTenpoPointer + 1;
//						continue;
//				}
//
//			}
//			oneRowBean.setTenpoList(newTenpoList);
//		}
//
//		
//		return tenbetuSiiresakiMstList;
//	}
//	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑

//	↓↓2007.01.21 H.Yamamoto カスタマイズ修正↓↓
	/**
	 * 検索処理を実施する
	 * @param  mst990101_KeepBean gamenInfo 画面情報保持Bean
	 * @return BeanHolder 検索結果
	 * @exception SQLException
	 */
	public BeanHolder search(mst990101_KeepBean gamenInfo, int displayRows) throws SQLException{
		
		BeanHolder bh = new BeanHolder(new mst990101_MakerCdTenbetuDM());
		DataHolder dh = new DataHolder();
		dh.updateParameterValue("bumon_cd", StringUtility.charFormat(gamenInfo.getBumonCd(),4,"0",true));
		dh.updateParameterValue("groupno_cd", gamenInfo.getGroupNoCd());
		dh.updateParameterValue("maker_cd", gamenInfo.getMakerCd());
		dh.updateParameterValue("siiresaki_cd", gamenInfo.getSiiresakiCd());
		bh.setDataHolder(dh);

		this.logger.info("店別仕入先マスタの検索処理を開始します。");
		List dbl = bh.getFirstPageBeanList();
		this.logger.info("店別仕入先マスタの検索処理を終了しました。");

		this.logger.info("店別仕入先マスタから取得したデータのキャッシュ処理を開始します。");
		BeanHolder bhReturn = new BeanHolder();
		bhReturn.setRowsInPage(displayRows);
		bhReturn.setCache(true);
		bhReturn.setCacheBeanList(dbl);
		this.logger.info("店別仕入先マスタから取得したデータのキャッシュ処理を終了します。");
		
		return bhReturn;
	}
//	↑↑2007.01.21 H.Yamamoto カスタマイズ修正↑↑

}
