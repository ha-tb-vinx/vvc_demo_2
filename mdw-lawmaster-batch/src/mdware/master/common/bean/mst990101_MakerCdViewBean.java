package mdware.master.common.bean;

import java.util.ArrayList;
import java.util.List;
//↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
import java.util.Iterator;
//↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;

/**
 * <p>タイトル: 画面に1行分のデータを表示する際に使用するBeanです</p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author K.Tanigawa
 * @version 1.0 (Create time: 2006/10/16 10:39:22) K.Tanigawa
 */
public class mst990101_MakerCdViewBean
{
	private static final StcLog stcLog = StcLog.getInstance();

	public static final int TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH = 4;
	public static final int TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH = 80;
	public static final int SIIRESAKI_CD_WITH_TIKU_MAX_LENGTH = 9;
	public static final int SIIRESAKI_CD_MAX_LENGTH = 6;
	public static final int TIKU_CD_MAX_LENGTH = 3;
	public static final int SIIRESAKI_NA_MAX_LENGTH = 80;
	public static final int TENPO_CD_MAX_LENGTH = 6;

	private String ten_siiresaki_kanri_cd = null;
	private String ten_siiresaki_kanri_na = null;
	private String siiresaki_cd_with_tiku = null;
	private String siiresaki_cd = null;
	private String tiku_cd = null;
	private String siiresaki_na = null;
	private long tenpo_count_num = 0;
	private String tenpo_cd = null;

	private List tenpoList = null;
	
	public mst990101_MakerCdViewBean(){
		this.tenpoList = new ArrayList();
	}
	
//	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
	public mst990101_MakerCdViewBean(List tenpoList){
		this.tenpoList = new ArrayList();
		if (tenpoList != null && tenpoList.size() > 0) {
//			for( Iterator ite = tenpoList.iterator(); ite.hasNext();){
//				this.tenpoList.add(((mst990101_RTenGroupBean)ite.next()).createClone());
//			}
			for (int i = 0; i < tenpoList.size(); i++) {
				mst990101_RTenGroupBean tenpoBean = (mst990101_RTenGroupBean)tenpoList.get(i);
				mst990101_TenpoBean newTenpoBean = new mst990101_TenpoBean();
				newTenpoBean.setTenpoCd(tenpoBean.getTenpoCd());
				this.tenpoList.add( newTenpoBean );
			}
		}
	}
//	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
		
	// ten_siiresaki_kanri_cdに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriCd(String ten_siiresaki_kanri_cd)
	{
		this.ten_siiresaki_kanri_cd = ten_siiresaki_kanri_cd;
		return true;
	}
	public String getTenSiiresakiKanriCd()
	{
		return cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriCdString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_cd,TEN_SIIRESAKI_KANRI_CD_MAX_LENGTH));
	}


	// ten_siiresaki_kanri_naに対するセッターとゲッターの集合
	public boolean setTenSiiresakiKanriNa(String ten_siiresaki_kanri_na)
	{
		this.ten_siiresaki_kanri_na = ten_siiresaki_kanri_na;
		return true;
	}
	public String getTenSiiresakiKanriNa()
	{
		return cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH);
	}
	public String getTenSiiresakiKanriNaString()
	{
		return "'" + cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH) + "'";
	}
	public String getTenSiiresakiKanriNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.ten_siiresaki_kanri_na,TEN_SIIRESAKI_KANRI_NA_MAX_LENGTH));
	}


	// siiresaki_cd_with_tikuに対するセッターとゲッターの集合
	public boolean setSiiresakiCdWithTiku(String siiresaki_cd_with_tiku)
	{
		this.siiresaki_cd_with_tiku = siiresaki_cd_with_tiku;
		
		if( this.siiresaki_cd_with_tiku != null && this.siiresaki_cd_with_tiku.trim().length() > 0 ){

			this.siiresaki_cd_with_tiku = this.siiresaki_cd_with_tiku.trim();
			if( this.siiresaki_cd_with_tiku.length() >= 6 ){
				this.siiresaki_cd = this.siiresaki_cd_with_tiku.substring(0, 6);
				this.tiku_cd      = this.siiresaki_cd_with_tiku.substring(6, this.siiresaki_cd_with_tiku.length());
			}else{
				this.siiresaki_cd = this.siiresaki_cd_with_tiku;
				this.tiku_cd      = "";
			}
		}
		
		
		return true;
	}
	public String getSiiresakiCdWithTiku()
	{
		return cutString(this.siiresaki_cd_with_tiku,SIIRESAKI_CD_WITH_TIKU_MAX_LENGTH);
	}
	public String getSiiresakiCdWithTikuString()
	{
		return "'" + cutString(this.siiresaki_cd_with_tiku,SIIRESAKI_CD_WITH_TIKU_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdWithTikuHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd_with_tiku,SIIRESAKI_CD_WITH_TIKU_MAX_LENGTH));
	}

	// siiresaki_cdに対するセッターとゲッターの集合
	private boolean setSiiresakiCd(String siiresaki_cd)
	{
		this.siiresaki_cd = siiresaki_cd;
		return true;
	}
	public String getSiiresakiCd()
	{
		return cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH);
	}
	public String getSiiresakiCdString()
	{
		return "'" + cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH) + "'";
	}
	public String getSiiresakiCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_cd,SIIRESAKI_CD_MAX_LENGTH));
	}

	// tiku_cdに対するセッターとゲッターの集合
	private boolean setTikuCd(String tiku_cd)
	{
		this.tiku_cd = tiku_cd;
		return true;
	}
	public String getTikuCd()
	{
		return cutString(this.tiku_cd,TIKU_CD_MAX_LENGTH);
	}
	public String getTikuCdString()
	{
		return "'" + cutString(this.tiku_cd,TIKU_CD_MAX_LENGTH) + "'";
	}
	public String getTikuCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tiku_cd,TIKU_CD_MAX_LENGTH));
	}
	
	// siiresaki_naに対するセッターとゲッターの集合
	public boolean setSiiresakiNa(String siiresaki_na)
	{
		this.siiresaki_na = siiresaki_na;
		return true;
	}
	public String getSiiresakiNa()
	{
		return cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH);
	}
	public String getSiiresakiNaString()
	{
		return "'" + cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH) + "'";
	}
	public String getSiiresakiNaHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.siiresaki_na,SIIRESAKI_NA_MAX_LENGTH));
	}


	// tenpo_count_numに対するセッターとゲッターの集合
	public boolean setTenpoCountNum(String tenpo_count_num)
	{
		try
		{
			this.tenpo_count_num = Long.parseLong(tenpo_count_num);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	public boolean setTenpoCountNum(long tenpo_count_num)
	{
		this.tenpo_count_num = tenpo_count_num;
		return true;
	}
	public long getTenpoCountNum()
	{
		return this.tenpo_count_num;
	}
	public String getTenpoCountNumString()
	{
		return Long.toString(this.tenpo_count_num);
	}

	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH);
	}
	public String getTenpoCdString()
	{
		return "'" + cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH) + "'";
	}
	public String getTenpoCdHTMLString()
	{
		return HTMLStringUtil.convert(cutString(this.tenpo_cd,TENPO_CD_MAX_LENGTH));
	}

	
	/**
	 * tenpoList を戻します。
	 * @return tenpoList
	 */
	public List getTenpoList() {
		return tenpoList;
	}
	/**
	 * tenpoList を設定
	 * @param tenpoList
	 */
	public void setTenpoList(List tenpoList) {
		this.tenpoList = tenpoList;
	}

//	↓↓2007.01.19 H.Yamamoto カスタマイズ修正↓↓
//	/**
//	 * tenpoCd(1店舗コードを既存のリストに追加) を設定
//	 * @param tenpoList
//	 */
//	public void setTenpoList( mst990101_TenpoBean tenpoBean ){
//		this.tenpoList.add(tenpoBean);
//	}

	/**
	 * tenpoCd(1店舗コードを既存のリストに更新) を設定
	 * @param tenpoList
	 */
	public void setTenpoList( mst990101_MakerCdTenbetuBean makerBean ){
		for (int i = 0; i < this.tenpoList.size(); i++) {
			mst990101_TenpoBean tenpoBean = (mst990101_TenpoBean)this.tenpoList.get(i);
			if (tenpoBean.getTenpoCd().equals( makerBean.getTenpoCd() )) {
				tenpoBean.setRegistered(true);
				i = tenpoList.size();
				break;
			}
		}
	}
//	↑↑2007.01.19 H.Yamamoto カスタマイズ修正↑↑
	
	/**
	 * ObjectのtoStringをオーバーライドする。
	 * 内容全てをフラットな文字列する。
	 * @return String このクラスの全ての内容を文字列にし返す。
	 */
	public String toString()
	{
		return "  ten_siiresaki_kanri_cd = " + getTenSiiresakiKanriCdString()
			+ "  ten_siiresaki_kanri_na = " + getTenSiiresakiKanriNaString()
			+ "  siiresaki_cd = " + getSiiresakiCdWithTikuString()
			+ "  siiresaki_na = " + getSiiresakiNaString()
			+ "  tenpo_count_num = " + getTenpoCountNumString()
			+ "  tenpo_cd = " + getTenpoCdString()
			+ "  tenpoList = " + tenpoList.toString();
	}
	/**
	 * Objectのcloneと同様の動作を行うがObjectのメソッドはprotectedなのでpublicで別メソッドを作成した。
	 * このクラスをインスタンス化し内容をすべてセットし返す。
	 * @return Mst990101MakerCdViewBean コピー後のクラス
	 */
	public mst990101_MakerCdViewBean createClone()
	{
		mst990101_MakerCdViewBean bean = new mst990101_MakerCdViewBean();
		bean.setTenSiiresakiKanriCd(this.ten_siiresaki_kanri_cd);
		bean.setTenSiiresakiKanriNa(this.ten_siiresaki_kanri_na);
		bean.setSiiresakiCdWithTiku(this.siiresaki_cd_with_tiku);
		bean.setSiiresakiNa(this.siiresaki_na);
		bean.setTenpoCountNum(this.tenpo_count_num);
		bean.setTenpoCd(this.tenpo_cd);
		bean.setTenpoList(this.tenpoList);
		return bean;
	}

	/**
	 * Objectのequalsをオーバーライドする。
	 * 内容がまったく同じかどうかを返す。
	 * @param Object 比較を行う対象
	 * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
	 */
	public boolean equals( Object o )
	{
		if( o == null ) return false;
		if( !( o instanceof mst990101_MakerCdViewBean ) ) return false;
		return this.toString().equals( o.toString() );
	}
	/**
	 * 文字列を最大バイト数で判断しはみ出した部分を削除する。
	 * このとき全角の半端な場所になる時はその文字の前でカットされる。
	 * @param String カットされる文字列
	 * @param int 最大バイト数
	 * @return String カット後の文字列
	 */
	private String cutString( String base, int max )
	{
		if( base == null ) return null;
		String wk = "";
		for( int pos = 0,count = 0; pos < max && pos < base.length(); pos++ )
		{
			try
			{
				byte bt[] = base.substring(pos,pos+1).getBytes("Shift_JIS");
				count += bt.length;
				if( count > max )
					break;
				wk += base.substring(pos,pos+1);
			}
			catch(Exception e)
			{
				stcLog.getLog().debug(this.getClass().getName() + "/cutString/" + base + "/" + base.substring(pos,pos+1) + "をShift_JISに変換できませんでした。");
			}
		}
		return wk;
	}
}
