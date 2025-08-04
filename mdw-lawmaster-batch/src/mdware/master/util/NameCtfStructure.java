/*
 * 作成日: 2006/10/24
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package mdware.master.util;

/**
 * @author totyu
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class NameCtfStructure
{
	private String syubetu;
	private String code;

	/**
	 * 
	 */
	public NameCtfStructure()
	{
		super();
	}

	/**
	 * 
	 */
	public NameCtfStructure(String syubetu, String code)
	{
		super();
		this.syubetu = syubetu;
		this.code = code;
	}

	/**
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return
	 */
	public String getSyubetu()
	{
		return syubetu;
	}

	/**
	 * @param string
	 */
	public void setCode(String string)
	{
		code = string;
	}

	/**
	 * @param string
	 */
	public void setSyubetu(String string)
	{
		syubetu = string;
	}

	public String toString()
	{
		return syubetu + "-" + code;
	}
}
