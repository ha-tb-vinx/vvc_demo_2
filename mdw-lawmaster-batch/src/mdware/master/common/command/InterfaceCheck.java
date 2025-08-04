package mdware.master.common.command;

/**
 * <P>タイトル:  登録票アップロードチェック処理</P>
 * <P>説明:      登録票アップロードチェック処理インターフェース。</P>
 * <P>著作権:	 Copyright (c) 2006</P>
 * <P>会社名:	 Vinculum Japan Corp.</P>
 * @author      jiangb
 * @version     1.0
 */
public interface InterfaceCheck {
	public void dataCheck() throws Exception;
	public void process() throws Exception;
}
