/**
 * <P>タイトル : 新ＭＤシステム　マスターメンテナンス 登録票ダウンロード画面項目格納用クラス</P>
 * <P>説明 : 新ＭＤシステムで使用する登録票アップロード画面の項目格納用クラス</P>
 * <P>著作権: Copyright (c) 2005</p>
 * <P>会社名: Vinculum Japan Corp.</P>
 * @author shimoyama
 * @version 1.0
 * @see なし
 */
package mdware.master.common.bean;

import jp.co.vinculumjapan.stc.log.StcLog;
import java.util.*;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

public class mst090101_KeepBean {
	private static final StcLog stcLog = StcLog.getInstance();

	private String ErrorFlg = ""; // エラーフラグ
	private String ErrorMessage = ""; // エラーメッセージ
	private String[] MenuBar = null; // メニューバーアイテム
	private Map CtrlColor = new HashMap(); // コントロール前景色
	private String FirstFocus = ""; // フォーカスを最初に取得するオブジェクト名
	private String movepos = ""; //ページング

	// ErrorFlgに対するセッターとゲッターの集合
	public boolean setErrorFlg(String ErrorFlg) {
		this.ErrorFlg = ErrorFlg;
		return true;
	}
	public String getErrorFlg() {
		return this.ErrorFlg;
	}

	// ErrorMessageに対するセッターとゲッターの集合
	public boolean setErrorMessage(String ErrorMessage) {
		this.ErrorMessage = ErrorMessage;
		return true;
	}
	public String getErrorMessage() {
		return this.ErrorMessage;
	}
	// MenuBarに対するセッターとゲッターの集合
	public boolean setMenuBar(String[] MenuBar) {
		this.MenuBar = MenuBar;
		return true;
	}
	public String[] getMenuBar() {
		return this.MenuBar;
	}
	// CtrlColorに対するセッターとゲッターの集合
	public boolean setCtrlColor(Map CtrlColor) {
		this.CtrlColor = CtrlColor;
		return true;
	}
	public Map getCtrlColor() {
		return this.CtrlColor;
	}

	// FirstFocusに対するセッターとゲッターの集合
	public boolean setFirstFocus(String FirstFocus) {
		this.FirstFocus = FirstFocus;
		return true;
	}
	public String getFirstFocus() {
		return this.FirstFocus;
	}

	// moveposに対するセッターとゲッターの集合
	public boolean setMovePos(String movepos) {
		this.movepos = movepos;
		return true;
	}
	public String getMovePos(){
		return this.movepos;
	}

	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString(String base, int max) {
		if (base == null)
			return null;
		String wk = "";
		for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) {
			try {
				byte bt[] = base.substring(pos, pos + 1).getBytes("UTF-8");
				count += bt.length;
				if (count > max)
					break;
				wk += base.substring(pos, pos + 1);
			} catch (Exception e) {
				stcLog.getLog().debug(
					this.getClass().getName()
						+ "/cutString/"
						+ base
						+ "/"
						+ base.substring(pos, pos + 1)
						+ "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}

}
