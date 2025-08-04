/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst540101用店発注納品不可能日の画面項目(一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst540101用店発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.ArrayList;


/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス  mst540101用店発注納品不可能日の画面項目(一覧)格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用するmst540101用店発注納品不可能日の画面項目格納用クラス</P>
 *  <P>著作権: Copyright (c) 2005</p>								
 *  <P>会社名: Vinculum Japan Corp.</P>								
 * @author Sirius T.Kiiuchi
 * @version 1.0
 * @see なし								
 */
public class mst540301_KeepMeisaiBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	private ArrayList Meisai	= new ArrayList();	// 一覧の明細

	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(ArrayList Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public ArrayList getMeisai() {
		return this.Meisai;
	}

	

}
