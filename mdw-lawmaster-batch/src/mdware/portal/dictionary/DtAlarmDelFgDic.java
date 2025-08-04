package mdware.portal.dictionary;

public class DtAlarmDelFgDic
{
	private String code;
	private String name;

	private DtAlarmDelFgDic(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}
	
	public String getName()
	{
		return name;
	}

	public static final DtAlarmDelFgDic MISAKUJO = new DtAlarmDelFgDic("0", "未削除");
	public static final DtAlarmDelFgDic SAKUJOZUMI = new DtAlarmDelFgDic("1", "削除済");
	public static final DtAlarmDelFgDic SYONINZUMI = new DtAlarmDelFgDic("2", "承認済");
}
