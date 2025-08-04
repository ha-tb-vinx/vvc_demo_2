package mdware.master.properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;

/**
 * <p>タイトル: MasterCommonPropertiesマネージャ</p>
 * <p>説明: master_common.propertiesファイルのデータを保持するクラス</p>
 * <p>著作権: Copyright  (C) 2006</p>
 * <p>会社名: Vinculum Japan Corporation</p>
 *
 * @see    キー値を取得する際は、MasterCommonPropertiesParameterクラスを使用して下さい
 * @author k_tanigawa
 * @version 1.0 (2006/09/28) 初版作成
 */
public class MasterCommonPropertiesManager {

	private static final StcLog stcLog = StcLog.getInstance();	//ログ出力用
	private static final MasterCommonPropertiesManager _INSTANCE = new MasterCommonPropertiesManager();	//シングルトンインスタンス作成
	private static final HashMap hmRowData = new HashMap();		//ファイルのパラメータ格納用HashMapオブジェクト
	private static boolean initialized = false;					//初期化状態判定用フラグ(true：初期済状態、false：未初期化状態)

	private MasterCommonPropertiesManager() {
	}

	//プロパティファイル内容読み込み
	public static MasterCommonPropertiesManager getInstance() {
		
		synchronized (_INSTANCE) {
			
			if( initialized ){
				return _INSTANCE;
			}
			
			FileReader fr = null;
			BufferedReader br = null;
			try {
				File file = new File(StcLibProperty.propertiesDir,"mdware");
				file = new File( file, "master");
				file = new File( file, "master_common.properties");
//				System.out.println("file："+file.getAbsolutePath());
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String line = null;
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if ((line.startsWith("#")) || (line.length() == 0)) {
						continue; // コメント行は飛ばす
					}

					//カンマでキーが分かれているため、
					//取得した一行分のデータをカンマで分割し、HashMapオブジェクトに追加
					StringTokenizer st = new StringTokenizer(line, "=");
					while (st.hasMoreTokens()) {
						String key = st.nextToken();
						String value = st.nextToken();

						hmRowData.put(key.trim(), value.trim());
//						System.out.println("HashMapオブジェクトに格納しました："+key.trim()+", "+value.trim());
					}
				}
			} catch (Exception e) {
				stcLog.getLog().warn("master_common.propertiesプロパティファイル取得時にエラーが発生しました。処理は続行します。", e);
			} finally {
				try {if (br != null) br.close(); } catch (Exception e) {}
				try {if (fr != null) fr.close(); } catch (Exception e) {}
			}
			initialized = true;
		}
		return _INSTANCE;
	}

	// 格納したパラメータ取得
	public String getParameter(String key) {
		return (hmRowData.get(key) == null ? "" : (String) hmRowData.get(key));
	}

	public void insideInfo(){
		
		Set k = hmRowData.keySet();
		for( Iterator ite =k.iterator(); ite.hasNext();){
			String key = (String)ite.next();
			System.out.println("key："+key+"　"+"parameter："+hmRowData.get(key));
		}
	}
	
	//テスト用メインメソッド
	public static void main(String args[]) {

		String propertyDir = "C:/eclipse_3.1.2/workspace/mdware/defaultroot/WEB-INF/properties";
		String executeMode = "release";
		String databaseUse = "use";

		StcLibProperty.propertiesDir = propertyDir;
		new StcLibProperty(executeMode, databaseUse, "MS932");

		MasterCommonPropertiesManager csfReader = MasterCommonPropertiesManager.getInstance();
		System.out.println("一発目");
		csfReader.insideInfo();	//中出し
		
		csfReader = MasterCommonPropertiesManager.getInstance();
		System.out.println("二発目");
		csfReader.insideInfo();	//中出し
	}

}
