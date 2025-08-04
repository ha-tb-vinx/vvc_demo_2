package mdware.common.util;

import java.util.*;
import java.sql.*;

import mdware.common.bean.SystemConfBean;
import mdware.common.bean.SystemConfDM;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;

/**
 * <p>タイトル: SystemConfBeanHolder</p>
 * <p>説明: メモリ上に常駐している SystemConfBean を返します。2006/05/01以降使用禁止</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author 未入力
 * @version 1.0
 */

public class SystemConfBeanHolder {
	private static boolean initilized = false;
	private static SystemConfBean sysBean = null;

	/**
	 * コンストラクタ。最初のアクセス時のみ初期処理が実行されます。
	 */
    public SystemConfBeanHolder() {
		if (!initilized) {
			try {
				init();
			} catch (SQLException e) {
			}
		}
    }

	/**
	 * 初期処理。システムパラメータよりデータを取得し、内部変数に保持します。
	 * @throws SQLException
	 */
	private void init() throws java.sql.SQLException {
		SystemConfDM sysConfDM = new SystemConfDM();
		BeanHolder sysConfBh = new BeanHolder(sysConfDM);
		sysConfBh.setRowsInPage(0);
		DataHolder dh = new DataHolder();
		sysConfBh.setDataHolder(dh);
		// システムパラメータデータの取得
		List sysConfList = sysConfBh.getFirstPageBeanList();
		sysConfBh.close();

		sysBean = (SystemConfBean)sysConfList.get(0);

		initilized = true;
	}

	/**
	 * SystemConfBeanを返します。
	 * @return メモリ上にあるSystemConfBean
	 */
	public SystemConfBean getSysConfBean() {
		return sysBean;
	}

	/**
	 * 配信先別データ保管ディレクトリを返します。
	 * @return 配信先別データ保管ディレクトリ
	 */
	public String getDataStoredDirPath(String dataSyubetuCd, String kouriCd, String haisinsakiCd ) {
		return sysBean.getDataHomeDirTx() + "/" +
				dataSyubetuCd + "/" + kouriCd + "/" + haisinsakiCd;
	}

	/**
	 * ダウンロード用データ保管ディレクトリを返します。
	 * @return 配信先別データ保管ディレクトリ
	 */
	public String getDownloadDirPath(String dataSyubetuCd, String kouriCd, String haisinsakiCd ) {
		return sysBean.getDownloadDirTx() + "/" +
				dataSyubetuCd + "/" + kouriCd + "/" + haisinsakiCd;
	}

	/**
	 * ダウンロード用ファイルパスを返します。
	 * 　例えば　"e:/aaa/bbb.dat"
	 * というファイルパスを引数に与えて、
	 * 　システムパラメータ.データ保管ディレクトリ＝"e:/aaa"
	 * 　システムパラメータ.ダウンロード用ディレクトリ＝"f:/aaa"
	 * ならば、
	 * 　"f:/aaa/bbb.dat"
	 * という文字列を返します。
	 * 　これはDBサーバから見てローカルのeドライブに存在するファイルがWebサーバのネットワークドライブを介してどのように
	 * パスが指定されるかを表します。
	 * @param dataFilePath
	 * @return
	 */
	public String getDownloadDirPath(String dataFilePath) {
		int pos = dataFilePath.indexOf(sysBean.getDataHomeDirTx());
		if (pos != 0) {
			return dataFilePath;
		}
		int from = sysBean.getDataHomeDirTx().trim().length();
		return sysBean.getDownloadDirTx() + dataFilePath.substring(from);
	}

	/**
	 * ダウンロード用ファイルパスを返します。
	 * 　例えば　"f:/aaa/bbb.dat" ←Webサーバから見たファイルパス
	 * というファイルパスを引数に与えて、
	 * 　システムパラメータ.データ保管ディレクトリ＝"e:/aaa"
	 * 　システムパラメータ.ダウンロード用ディレクトリ＝"f:/aaa"
	 * ならば、
	 * 　"e:/aaa/bbb.dat"
	 * という文字列を返します。
	 * 　これはDBサーバから見たローカルファイルパスを表します。
	 * @param downFilePath
	 * @return
	 */
	public String getDataStoredDirPath(String downFilePath) {
		int pos = downFilePath.indexOf(sysBean.getDownloadDirTx());
		if (pos != 0) {
			return downFilePath;
		}
		int from = sysBean.getDownloadDirTx().trim().length();
		return sysBean.getDataHomeDirTx() + downFilePath.substring(from);
	}

	public static void main(String args[]) {
		String propertyDir = "defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";
		String batchJobID = null;
		String[] parameters = null;

		mdware.common.batch.util.control.BatchController controller = new mdware.common.batch.util.control.BatchController();
		controller.init(propertyDir, executeMode, databaseUse);

		SystemConfBeanHolder scbh = new SystemConfBeanHolder();
		//System.out.println(scbh.getDownloadDirPath("C:/rbsitedata/datas/files/test/test1.dat"));
		System.out.println(scbh.getDataStoredDirPath("G:/rbsitedata/datas/files/test/test1.dat"));
	}
}