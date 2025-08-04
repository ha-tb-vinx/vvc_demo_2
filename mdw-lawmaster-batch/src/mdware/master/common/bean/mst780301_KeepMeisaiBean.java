/**
 * <p>タイトル	： 新ＭＤシステム（マスタ管理）店別品種(店舗)画面表示明細データ格納用クラス</p>
 * <p>説明		： 新ＭＤシステムで使用する店別品種(店舗)マスタの画面表示明細データ格納用クラス</p>
 * <p>著作権	： Copyright (c) 2005</p>
 * <p>会社名	： Vinculum Japan Corp.</p>
 * @author		： VJC A.Tozaki
 * @version	： 1.0(2005/11/01)初版作成
 */

package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.ArrayList;
import java.util.List;


public class mst780301_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private List Meisai	= new ArrayList();	// 一覧の明細
	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(List Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public List getMeisai() {
		return this.Meisai;
	}
}