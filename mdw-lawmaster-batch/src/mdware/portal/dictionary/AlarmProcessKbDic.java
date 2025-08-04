package mdware.portal.dictionary;

import jp.co.vinculumjapan.mdware.common.util.MessageUtil;
import mdware.common.resorces.util.ResorceUtil;
/**
  - ファイル名  : AlarmProcessKbDic.java
  -     タイトル        :
  - 説明                :
  - 著作権              : Copyright (c) 2013
  - 会社名              : VINX Corp.
  - 入力     なし
  - 出力     なし
  - 戻り値   なし
  - 注記   なし
  -
  - Author  VINX
  - @Version 1.00  (2014.09.24) Minh.NV 海外LAWSON様対応 英文化対応
 */
public class AlarmProcessKbDic
{
	private String code;
	private String name;

	private AlarmProcessKbDic(String code, String name)
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

	public static final AlarmProcessKbDic BATCH = new AlarmProcessKbDic("0", MessageUtil.getMessage("COMMON_TXT_00001",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
	public static final AlarmProcessKbDic ONLINE = new AlarmProcessKbDic("1", MessageUtil.getMessage("COMMON_TXT_00002",ResorceUtil.getInstance().getPropertie("USER_LOCAL")));
}
