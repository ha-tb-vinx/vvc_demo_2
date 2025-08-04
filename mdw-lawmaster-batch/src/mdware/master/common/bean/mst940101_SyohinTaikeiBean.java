/**
 * <p>タイトル: mst940101_SyohinTaikeiBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索のレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
package mdware.master.common.bean;

import java.util.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: mst940101_SyohinTaikeiBeanクラス</p>
 * <p>説明: 新ＭＤシステムで使用する商品体系検索のレコード格納用クラス</p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author K.Satobo
 * @version 1.0 (2006/04/10) 初版作成
 */
public class mst940101_SyohinTaikeiBean {

	private static final StcLog stcLog = StcLog.getInstance();

	//データの長さ
	public static final int YUKO_DT_MAX_LENGTH = 8;//有効日の長さ
	public static final int KAISOU_PATTERN_NM_MAX_LENGTH = 12;//階層パターンの長さ
	public static final int CODE_CD_MAX_LENGTH = 4;//コードの長さ
	public static final int CODE_NM_MAX_LENGTH = 20;//コード名称の長さ

	//画面(CSV)出力項目
	private String kaiso_pattern_nm = null;//階層パターン区分名
	private String yuko_dt = null;//検索対象日(有効日)
	private String hinsyu_cd = null;//品種コード
	private String hinsyu_nm = null;//品種名
	private String hingun_cd = null;//品群コード
	private String hingun_nm = null;//品群名
	private String hanku_cd = null;//販区コード
	private String hanku_nm = null;//販区名
	private String uriku_cd = null;//売区コード
	private String uriku_nm = null;//売区名
	private String tyukansyukei_cd = null;//中間集計コード
	private String tyukansyukei_nm = null;//中間集計名
	private String syogyosyu_cd = null;//小業種コード
	private String syogyosyu_nm = null;//小業種名
	private String daigyosyu_cd = null;//大業種コード
	private String daigyosyu_nm = null;//大業種名

	//画面制御項目
	private Map ctlColor = null;//画面コントロール色
	private String[] ctlname = null;//画面コントロール名

	//DBから抽出したBeanかどうかを保持する。主にDB更新を行う時に役に立つフラグ。
	private boolean createDatabase = false;
	protected void setCreateDatabase() {
		createDatabase = true;
	}
	public boolean isCreateDatabase() {
		return createDatabase;
	}

	/**
	 * R_SYOHINKAISO Beanを１件のみ抽出したい時に使用する
	 */
	public static mst940101_SyohinTaikeiBean getmst940101_SyohinTaikeiBean(DataHolder dataHolder) {
		mst940101_SyohinTaikeiBean bean = null;
		BeanHolder beanHolder = new BeanHolder(new mst940101_SyohinTaikeiDM());
		try {
			Iterator ite = beanHolder.getPageBeanListFromDataHolder(dataHolder).iterator();
			//１件以上存在する時はまず保存する
			if(ite.hasNext())
				bean = (mst940101_SyohinTaikeiBean)ite.next();
			//２件以上存在する時はＮＵＬＬにする
			if(ite.hasNext())
				bean = null;
		}
		catch(Exception e) {
		}
		return bean;
	}//getmst940101_SyohinTaikeiBean

	/******************************************************
	 *	画面、CSV出力項目
	 ******************************************************/

	/**
	 * 階層パターン区分名を格納する<br>
	 * @param String 階層パターン区分名
	 */
	public void setKaisoPatternNm(String kaiso_pattern_nm) {
		this.kaiso_pattern_nm = kaiso_pattern_nm;
	}

	/**
	 * 階層パターン区分名を取得する<br>
	 * @return 階層パターン区分名
	 */
	public String getKaisoPatternNm() {
		return cutString(kaiso_pattern_nm, KAISOU_PATTERN_NM_MAX_LENGTH);
	}

	/**
	 * 階層パターン区分名を取得する(文字)<br>
	 * @return 階層パターン区分名
	 */
	public String getKaisouPatternNmString() {
		return "'" + getKaisoPatternNm() + "'";
	}

	/**
	 * 階層パターン区分名を取得する(HTML表示)<br>
	 * @return 階層パターン区分名
	 */
	public String getKaisouPatternNmHTMLString() {
		return HTMLStringUtil.convert(getKaisoPatternNm());
	}

	/**
	 * 検索対象日(有効日)を格納する<br>
	 * @param String 検索対象日
	 */
	public void setYukoDt(String yuko_dt) {
		this.yuko_dt = yuko_dt;
	}

	/**
	 * 検索対象日(有効日)を取得する<br>
	 * @return 検索対象日
	 */
	public String getYukoDt() {
		return cutString(yuko_dt, YUKO_DT_MAX_LENGTH);
	}

	/**
	 * 検索対象日(有効日)を取得する(文字)<br>
	 * @return 検索対象日
	 */
	public String getYukoDtString() {
		return "'" + getYukoDt() + "'";
	}

	/**
	 * 検索対象日(有効日)を取得する(HTML表示)<br>
	 * @return 検索対象日
	 */
	public String getYukoDtHTMLString() {
		return HTMLStringUtil.convert(getYukoDt());
	}

	/**
	 * 品種コードを格納する<br>
	 * @param String 品種コード
	 */
	public void setHinsyuCd(String hinsyu_cd) {
		this.hinsyu_cd = hinsyu_cd;
	}

	/**
	 * 品種コードを取得する<br>
	 * @return 品種コード
	 */
	public String getHinsyuCd() {
		return cutString(hinsyu_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 品種コードを取得する(文字)<br>
	 * @return 品種コード
	 */
	public String getHinsyuCdString() {
		return "'" + getHinsyuCd() + "'";
	}

	/**
	 * 品種コードを取得する(HTML表示)<br>
	 * @return 品種コード
	 */
	public String getHinsyuCdHTMLString() {
		return HTMLStringUtil.convert(getHinsyuCd());
	}

	/**
	 * 品種名を格納する<br>
	 * @param String 品種名
	 */
	public void setHinsyuNm(String hinsyu_nm) {
		this.hinsyu_nm = hinsyu_nm;
	}

	/**
	 * 品種名を取得する<br>
	 * @return 品種名
	 */
	public String getHinsyuNm() {
		return cutString(hinsyu_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 品種名を取得する(文字)<br>
	 * @return 品種名
	 */
	public String getHinsyuNmString() {
		return "'" + getHinsyuNm() + "'";
	}

	/**
	 * 品種名を取得する(HTML表示)<br>
	 * @return 品種名
	 */
	public String getHinsyuNmHTMLString() {
		return HTMLStringUtil.convert(getHinsyuNm());
	}

	/**
	 * 品群コードを格納する<br>
	 * @param String 品群コード
	 */
	public void setHingunCd(String hingun_cd) {
		this.hingun_cd = hingun_cd;
	}

	/**
	 * 品群コードを取得する<br>
	 * @return 品群コード
	 */
	public String getHingunCd() {
		return cutString(hingun_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 品群コードを取得する(文字)<br>
	 * @return 品群コード
	 */
	public String getHingunCdString() {
		return "'" + getHingunCd() + "'";
	}

	/**
	 * 品群コードを取得する(HTML表示)<br>
	 * @return 品群コード
	 */
	public String getHingunCdHTMLString() {
		return HTMLStringUtil.convert(getHingunCd());
	}

	/**
	 * 品群名を格納する<br>
	 * @param String 品群名
	 */
	public void setHingunNm(String hingun_nm) {
		this.hingun_nm = hingun_nm;
	}

	/**
	 * 品群名を取得する<br>
	 * @return 品群名
	 */
	public String getHingunNm() {
		return cutString(hingun_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 品群名を取得する(文字)<br>
	 * @return 品群名
	 */
	public String getHingunNmString() {
		return "'" + getHingunNm() + "'";
	}

	/**
	 * 品群名を取得する(HTML表示)<br>
	 * @return 品群名
	 */
	public String getHingunNmHTMLString() {
		return HTMLStringUtil.convert(getHingunNm());
	}

	/**
	 * 販区コードを格納する<br>
	 * @param String 販区コード
	 * @return
	 */
	public void setHankuCd(String hanku_cd) {
		this.hanku_cd = hanku_cd;
	}

	/**
	 * 販区コードを取得する<br>
	 * @return 販区コード
	 */
	public String getHankuCd() {
		return cutString(hanku_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 販区コードを取得する(文字)<br>
	 * @return 販区コード
	 */
	public String getHankuCdString() {
		return "'" + getHankuCd() + "'";
	}

	/**
	 * 販区コードを取得する(HTML表示)<br>
	 * @return 販区コード
	 */
	public String getHankuCdHTMLString() {
		return HTMLStringUtil.convert(getHankuCd());
	}

	/**
	 * 販区名を格納する<br>
	 * @param String 販区名
	 */
	public void setHankuNm(String hanku_nm) {
		this.hanku_nm = hanku_nm;
	}

	/**
	 * 販区名を取得する<br>
	 * @return 販区名
	 */
	public String getHankuNm() {
		return cutString(hanku_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 販区名を取得する(文字)<br>
	 * @return 販区名
	 */
	public String getHankuNmString() {
		return "'" + getHankuNm() + "'";
	}

	/**
	 * 販区名を取得する(HTML表示)<br>
	 * @return 販区名
	 */
	public String getHankuNmHTMLString() {
		return HTMLStringUtil.convert(getHankuNm());
	}

	/**
	 * 売区コードを格納する<br>
	 * @param String 売区コード
	 */
	public void setUrikuCd(String uriku_cd) {
		this.uriku_cd = uriku_cd;
	}

	/**
	 * 売区コードを取得する<br>
	 * @return 売区コード
	 */
	public String getUrikuCd() {
		return cutString(uriku_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 売区コードを取得する(文字)<br>
	 * @return 売区コード
	 */
	public String getUrikuCdString() {
		return "'" + getUrikuCd() + "'";
	}

	/**
	 * 売区コードを取得する(HTML表示)<br>
	 * @return 売区コード
	 */
	public String getUrikuCdHTMLString() {
		return HTMLStringUtil.convert(getUrikuCd());
	}

	/**
	 * 売区名を格納する<br>
	 * @param String 売区名
	 */
	public void setUrikuNm(String uriku_nm) {
		this.uriku_nm = uriku_nm;
	}

	/**
	 * 売区名を取得する<br>
	 * @return 売区名
	 */
	public String getUrikuNm() {
		return cutString(uriku_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 売区名を取得する(文字)<br>
	 * @return 売区名
	 */
	public String getUrikuNmString() {
		return "'" + getUrikuNm() + "'";
	}

	/**
	 * 売区名を取得する(HTML表示)<br>
	 * @return 売区名
	 */
	public String getUrikuNmHTMLString() {
		return HTMLStringUtil.convert(getUrikuNm());
	}

	/**
	 * 中間集計コードを格納する<br>
	 * @param String 中間集計コード
	 */
	public void setTyukansyukeiCd(String tyukansyukei_cd) {
		this.tyukansyukei_cd = tyukansyukei_cd;
	}

	/**
	 * 中間集計コードを取得する<br>
	 * @return 中間集計コード
	 */
	public String getTyukansyukeiCd() {
		return cutString(tyukansyukei_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 中間集計コードを取得する(文字)<br>
	 * @return 中間集計コード
	 */
	public String getTyukansyukeiCdString() {
		return "'" + getTyukansyukeiCd() + "'";
	}

	/**
	 * 中間集計コードを取得する(HTML表示)<br>
	 * @return 中間集計コード
	 */
	public String getTyukansyukeiCdHTMLString() {
		return HTMLStringUtil.convert(getTyukansyukeiCd());
	}

	/**
	 * 中間集計名を格納する<br>
	 * @param String 中間集計名
	 */
	public void setTyukansyukeiNm(String tyukansyukei_nm) {
		this.tyukansyukei_nm = tyukansyukei_nm;
	}

	/**
	 * 中間集計名を取得する<br>
	 * @return 中間集計名
	 */
	public String getTyukansyukeiNm() {
		return cutString(tyukansyukei_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 中間集計名を取得する(文字)<br>
	 * @return 中間集計名
	 */
	public String getTyukansyukeiNmString() {
		return "'" + getTyukansyukeiNm() + "'";
	}

	/**
	 * 中間集計名を取得する(HTML表示)<br>
	 * @return 中間集計名
	 */
	public String getTyukansyukeiNmHTMLString() {
		return HTMLStringUtil.convert(getTyukansyukeiNm());
	}

	/**
	 * 小業種コードを格納する<br>
	 * @param String 小業種コード
	 */
	public void setSyogyosyuCd(String syogyosyu_cd) {
		this.syogyosyu_cd = syogyosyu_cd;
	}

	/**
	 * 小業種コードを取得する<br>
	 * @return 小業種コード
	 */
	public String getSyogyosyuCd() {
		return cutString(syogyosyu_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 小業種コードを取得する(文字)<br>
	 * @return 小業種コード
	 */
	public String getSyogyosyuCdString() {
		return "'" + getSyogyosyuCd() + "'";
	}

	/**
	 * 小業種コードを取得する(HTML表示)<br>
	 * @return 小業種コード
	 */
	public String getSyogyosyuCdHTMLString() {
		return HTMLStringUtil.convert(getSyogyosyuCd());
	}

	/**
	 * 小業種名を格納する<br>
	 * @param String 小業種名
	 */
	public void setSyogyosyuNm(String syogyosyu_nm) {
		this.syogyosyu_nm = syogyosyu_nm;
	}

	/**
	 * 小業種名を取得する<br>
	 * @return 小業種名
	 */
	public String getSyogyosyuNm() {
		return cutString(syogyosyu_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 小業種名を取得する(文字)<br>
	 * @return 小業種名
	 */
	public String getSyogyosyuNmString() {
		return "'" + getSyogyosyuNm() + "'";
	}

	/**
	 * 小業種名を取得する(HTML表示)<br>
	 * @return 小業種名
	 */
	public String getSyogyosyuNmHTMLString() {
		return HTMLStringUtil.convert(getSyogyosyuNm());
	}

	/**
	 * 大業種コードを格納する<br>
	 * @param String 大業種コード
	 */
	public void setDaigyosyuCd(String daigyosyu_cd) {
		this.daigyosyu_cd = daigyosyu_cd;
	}

	/**
	 * 大業種コードを取得する<br>
	 * @return 大業種コード
	 */
	public String getDaigyosyuCd() {
		return cutString(daigyosyu_cd, CODE_CD_MAX_LENGTH);
	}

	/**
	 * 大業種コードを取得する(文字)<br>
	 * @return 大業種コード
	 */
	public String getDaigyosyuCdString() {
		return "'" + getDaigyosyuCd() + "'";
	}

	/**
	 * 大業種コードを取得する(HTML表示)<br>
	 * @return 大業種コード
	 */
	public String getDaigyosyuCdHTMLString() {
		return HTMLStringUtil.convert(getDaigyosyuCd());
	}

	/**
	 * 大業種名を格納する<br>
	 * @param String 大業種名
	 */
	public void setDaigyosyuNm(String daigyosyu_nm) {
		this.daigyosyu_nm = daigyosyu_nm;
	}

	/**
	 * 大業種名を取得する<br>
	 * @return 大業種名
	 */
	public String getDaigyosyuNm() {
		return cutString(daigyosyu_nm, CODE_NM_MAX_LENGTH);
	}

	/**
	 * 大業種名を取得する(文字)<br>
	 * @return 大業種名
	 */
	public String getDaigyosyuNmString() {
		return "'" + getDaigyosyuNm() + "'";
	}

	/**
	 * 大業種名を取得する(HTML表示)<br>
	 * @return 大業種名
	 */
	public String getDaigyosyuNmHTMLString() {
		return HTMLStringUtil.convert(getDaigyosyuNm());
	}

	/******************************************************
	 *	画面制御項目
	 ******************************************************/

	/**
	 * 画面コントロール色を設定する。<br>
	 * @param Map 画面コントロール色
	 */
	public void setCtlColor(Map map) {
		this.ctlColor = map;
	}

	/**
	 * 画面コントロール色を取得する<br>
	 * @return 画面コントロール色
	 */
	public Map getCtlColor() {
		return ctlColor;
	}

	/**
	 * 画面コントロール名を設定する。<br>
	 * @param String[] 画面コントロール名
	 */
	public void setCtlname(String[] ctlname) {
		this.ctlname = ctlname;
	}

	/**
	 * 画面コントロール名を取得する<br>
	 * @return 画面コントロール名
	 */
	public String[] getCtlname() {
		return ctlname;
	}

	/******************************************************
	 *	その他のメソッド
	 ******************************************************/

	/**
	 * ObjectのtoStringをオーバーライドする。<br>
	 * 内容全てをフラットな文字列する。<br>
	 * @return このクラスの全ての内容を文字列にして返す。
	 */
	public String toString() {

		StringBuffer str = new StringBuffer();

		//画面(CSV)表示項目
		str.append(" kaiso_pattern_nm = " + getKaisouPatternNmString());
		str.append(" yuko_dt = " + getYukoDtString());
		str.append(" hinsyu_cd = " + getHinsyuCdString());
		str.append(" hinsyu_nm = " + getHinsyuNmString());
		str.append(" hingun_cd = " + getHingunCdString());
		str.append(" hingun_nm = " + getHingunNmString());
		str.append(" hanku_cd = " + getHankuCdString());
		str.append(" hanku_nm = " + getHankuNmString());
		str.append(" uriku_cd = " + getUrikuCdString());
		str.append(" uriku_nm = " + getUrikuNmString());
		str.append(" tyukansyukei_cd = " + getTyukansyukeiCdString());
		str.append(" tyukansyukei_nm = " + getTyukansyukeiNmString());
		str.append(" syogyosyu_cd = " + getSyogyosyuCdString());
		str.append(" syogyosyu_nm = " + getSyogyosyuNmString());
		str.append(" daigyosyu_cd = " + getDaigyosyuCdString());
		str.append(" daigyosyu_nm = " + getDaigyosyuNmString());

		//画面制御項目
		str.append(" ctlColor = " + getCtlColor());
		str.append(" ctlname = " + getCtlname().toString());
		str.append(" createDatabase = " + createDatabase);

		return str.toString();
	}//toString

	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。<br>
	 * このクラスをインスタンス化し内容をすべてセットし返す。<br>
	 * @return mst940101_SyohinTaikeiBean コピー後のクラス
	 */
	public mst940101_SyohinTaikeiBean createClone() {

		mst940101_SyohinTaikeiBean bean = new mst940101_SyohinTaikeiBean();

		//画面(CSV)表示項目
		bean.setKaisoPatternNm(this.kaiso_pattern_nm);
		bean.setYukoDt(this.yuko_dt);
		bean.setHinsyuCd(this.hinsyu_cd);
		bean.setHinsyuNm(this.hinsyu_nm);
		bean.setHingunCd(this.hingun_cd);
		bean.setHingunNm(this.hingun_nm);
		bean.setHankuCd(this.hanku_cd);
		bean.setHankuNm(this.hanku_nm);
		bean.setUrikuCd(this.uriku_cd);
		bean.setUrikuNm(this.uriku_nm);
		bean.setTyukansyukeiCd(this.tyukansyukei_cd);
		bean.setTyukansyukeiNm(this.tyukansyukei_nm);
		bean.setSyogyosyuCd(this.syogyosyu_cd);
		bean.setSyogyosyuNm(this.syogyosyu_nm);
		bean.setDaigyosyuCd(this.daigyosyu_cd);
		bean.setDaigyosyuNm(this.daigyosyu_nm);

		//画面制御項目
		bean.setCtlColor(this.ctlColor);
		bean.setCtlname(this.ctlname);

		if(this.isCreateDatabase()) {
			bean.setCreateDatabase();
		}
		return bean;
	}//createClone

	/**
	 * Objectのequalsをオーバーライドする。<br>
	 * 内容がまったく同じかどうかを返す。<br>
	 * @param Object 比較を行う対象
	 * @return 比較対照がnullまたは内容が違う時はfalseを返す。
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} 
		if (!(obj instanceof mst940101_SyohinTaikeiBean)) {
			return false;
		} 
		return this.toString().equals(obj.toString());
	}//equals

	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。<br>
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。<br>
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return カット後の文字列
	 */
	private String cutString(String base, int max) {

		if (base == null) {
			return "";
		}
		String wk = "";
		for (int pos=0, count=0; pos<max && pos<base.length(); pos++) {
			try {
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if (count > max) {
					break;
				}
				wk += base.substring(pos,pos+1);
			} catch (Exception e) {
				stcLog.getLog().error(
					this.getClass().getName() + "/cutString/" + base + "/" + 
					base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}//cutString
}