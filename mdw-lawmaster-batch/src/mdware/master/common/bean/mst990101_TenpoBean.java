package mdware.master.common.bean;
/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2006</p>
 * <p>会社名: </p>
 * @author K.Tanigawa
 * @version 1.0
 * @version 1.1 障害票№0266対応 Beanの軽量化
 */
public class mst990101_TenpoBean
{
	private String tenpo_cd = null;
	private String tenpo_na = null;
	private boolean bRegistered = false;
	private static final String BLANK = "";
	
	public mst990101_TenpoBean(){
	}
	public mst990101_TenpoBean(mst990101_MakerCdTenbetuBean bean){
		this.tenpo_cd = bean.getTenpoCd();
		this.bRegistered = true;
	}
	// tenpo_cdに対するセッターとゲッターの集合
	public boolean setTenpoCd(String tenpo_cd)
	{
		this.tenpo_cd = tenpo_cd;
		return true;
	}
	public String getTenpoCd()
	{
		return (this.tenpo_cd == null) ? BLANK : this.tenpo_cd;
	}
	// tenpo_naに対するセッターとゲッターの集合
	public boolean setTenpoNa(String tenpo_na)
	{
		this.tenpo_na = tenpo_na;
		return true;
	}
	public String getTenpoNa()
	{
		return (this.tenpo_na == null ) ? BLANK : this.tenpo_na;
	}
	/**
	 * bRegistered を戻します。
	 * @return bRegistered
	 */
	public boolean isRegistered() {
		return bRegistered;
	}
	/**
	 * registered を設定
	 * @param registered
	 */
	public void setRegistered(boolean registered) {
		bRegistered = registered;
	}
}
