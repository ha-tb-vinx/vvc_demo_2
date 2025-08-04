package mdware.portal.dictionary;

public class RRiyoUserRiyoUserSyubetuKbDic
{
	private String code;
	private String name;

	private RRiyoUserRiyoUserSyubetuKbDic(String code, String name)
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
	
	public static final RRiyoUserRiyoUserSyubetuKbDic TORIHIKISAKI 		= new RRiyoUserRiyoUserSyubetuKbDic("1", "取引先");
	public static final RRiyoUserRiyoUserSyubetuKbDic BUYER 				= new RRiyoUserRiyoUserSyubetuKbDic("2", "バイヤー");
	public static final RRiyoUserRiyoUserSyubetuKbDic TENPO_USER 		= new RRiyoUserRiyoUserSyubetuKbDic("3", "店舗ユーザ");
	public static final RRiyoUserRiyoUserSyubetuKbDic OTORIHIKISAKIKANRI	= new RRiyoUserRiyoUserSyubetuKbDic("4", "お取引先管理者");
	public static final RRiyoUserRiyoUserSyubetuKbDic HANSOKUKANRI 		= new RRiyoUserRiyoUserSyubetuKbDic("5", "販促管理");
	public static final RRiyoUserRiyoUserSyubetuKbDic SYSTEMKANRI 		= new RRiyoUserRiyoUserSyubetuKbDic("6", "システム管理");
	public static final RRiyoUserRiyoUserSyubetuKbDic VJCM2ADMIN			= new RRiyoUserRiyoUserSyubetuKbDic("Z", "VJCM2運用");
}
