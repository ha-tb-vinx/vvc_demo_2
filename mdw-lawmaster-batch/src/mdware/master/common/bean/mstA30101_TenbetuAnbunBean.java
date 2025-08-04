/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別按分率登録(一覧)表示データ(明細)格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別按分率登録(一覧)表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006.07.18)初版作成
 */
package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）店別按分率登録(一覧)の画面表示データ(明細)格納用クラス</p>
 * <p>説明: 新ＭＤシステムで使用する店別按分率登録(一覧)の画面表示データ(明細)格納用クラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author magp
 * @version 1.0(2006.07.18)初版作成
 */
public class mstA30101_TenbetuAnbunBean
{

	private List Meisai		= new ArrayList();	// 一覧の明細
	
	// Meisaiに対するセッターとゲッターの集合
	public boolean setMeisai(List Meisai) {
		this.Meisai = Meisai;
		return true;
	}
	
	public List getMeisai() {
		return this.Meisai;
	}
}

