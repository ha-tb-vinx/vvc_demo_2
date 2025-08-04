package mdware.common.dictionary;

public class DummyBumonCdDic
{
	private String code;
	private String name;

	private DummyBumonCdDic(String code, String name)
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

	public static final DummyBumonCdDic HANSOKUKANRI 		= new DummyBumonCdDic("998", "販促管理");
	public static final DummyBumonCdDic OTORIHIKISAKIKANRI 	= new DummyBumonCdDic("999", "お取引先管理者");
	public static final DummyBumonCdDic SYSTEMKANRI 			= new DummyBumonCdDic("997", "システム管理者");
	public static final DummyBumonCdDic VJCM2ADMIN 			= new DummyBumonCdDic("M2BM", "VJCM2運用");
}
