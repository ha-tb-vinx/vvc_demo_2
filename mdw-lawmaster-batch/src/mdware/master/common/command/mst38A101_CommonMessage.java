package mdware.master.common.command;

import java.util.HashMap;
import java.util.Map;

import mdware.common.resorces.util.ResorceUtil;
import jp.co.vinculumjapan.mdware.common.util.MessageUtil;

/**
 * <p>タイトル:商品マスタ生成バッチ共通メッセージクラス</p>
 * <p>説明:共通のメッセージを保持するクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/05/23<BR>
 * @version 1.01 2011/02/25 Y.Imai 計量器保存温度帯区分追加対応 MM00111
 * @Version 3.00 (2013.05.21) M.Ayukawa [MSTC00001] ランドローム様対応 メーカコード コード管理対応
 * @Version 3.01 (2014.01.23) K.TO [CUS00105] 売価変更フラグ対応（画面側）
 * @version 3.01 (2015.10.09) DAI.BQ FIVImart対応
 * @version 3.02 (2017.03.15) T.Arimoto #4358対応
 * @version 3.03 (2017.10.04) T.Arimoto #5994対応
 * @author shimoyama
 */

public class mst38A101_CommonMessage {

	public static Map MsgMap = null;

	public static Map getMsg() {

		if (MsgMap == null) {
			MsgMap = createMap();
		}
		return MsgMap;
	}

	private static Map createMap() {
		
		Map map = new HashMap();
//
		//map.put("0113", "部門コードを指定してください。");
		map.put("0113", "COMMONM_MSG_00014");
//		map.put("0114", "指定された部門が存在しません。");
		map.put("0114", "COMMONM_MSG_00015");
		map.put("0115", "COMMONM_MSG_00016");
		map.put("0116", "COMMONM_MSG_00017");
		map.put("0117", "COMMONM_MSG_00018");
		map.put("0118", "COMMONM_MSG_00019" );
//		map.put("0119", "ユニットコードを指定してください。");
		map.put("0119", "COMMONM_MSG_00020" );
//		map.put("0120", "指定されたユニットが存在しません。");
		map.put("0120", "COMMONM_MSG_00021" );
//		map.put("0121", "指定された部門に当該ユニットが存在しません。");
		map.put("0121", "COMMONM_MSG_00022" );
		map.put("0122", "COMMONM_MSG_00023" );
		map.put("0123", "COMMONM_MSG_00024" );
		map.put("0124", "COMMONM_MSG_00025" );
		map.put("0125", "COMMONM_MSG_00026" );
		map.put("0126", "COMMONM_MSG_00027" );
		map.put("0127", "COMMONM_MSG_00028" );
		map.put("0128", "COMMONM_MSG_00029" );
//		map.put("0129", "仕入先コードを指定してください。");
		map.put("0129", "COMMONM_MSG_00030" );
//		map.put("0130", "指定された仕入先は存在しません。");
		map.put("0130", "COMMONM_MSG_00031" );
		map.put("0131", "COMMONM_MSG_00032" );
		map.put("0132", "COMMONM_MSG_00033" );
		map.put("0133", "COMMONM_MSG_00034" );
		map.put("0134", "COMMONM_MSG_00035" );
		map.put("0135", "COMMONM_MSG_00036" );
		map.put("0136", "COMMONM_MSG_00037" );
		map.put("0137", "COMMONM_MSG_00038" );
		map.put("0138", "COMMONM_MSG_00039" );
		map.put("0139", "COMMONM_MSG_00040" );
		map.put("0140", "COMMONM_MSG_00041" );
		map.put("0141", "COMMONM_MSG_00042" );
		map.put("0142", "COMMONM_MSG_00043" );
		map.put("0143", "COMMONM_MSG_00044" );
		map.put("0144", "COMMONM_MSG_00045" );
		map.put("0145", "COMMONM_MSG_00046" );
		map.put("0146", "COMMONM_MSG_00047" );
		map.put("0147", "COMMONM_MSG_00048" );
		map.put("0148", "COMMONM_MSG_00049" );
		map.put("0149", "COMMONM_MSG_00050" );
		map.put("0150", "COMMONM_MSG_00051" );
		//map.put("0180", "PLU送信日が不正です。");
		map.put("0151", "COMMONM_MSG_00052" );
		map.put("0152", "COMMONM_MSG_00053" );
		map.put("0153", "COMMONM_MSG_00054" );
		map.put("0154", "COMMONM_MSG_00055" );
		map.put("0155", "COMMONM_MSG_00056" );
		map.put("0156", "COMMONM_MSG_00057" );
		map.put("0157", "COMMONM_MSG_00058" );
		map.put("0158", "COMMONM_MSG_00059" );
		map.put("0159", "COMMONM_MSG_00060" );
		map.put("0160", "COMMONM_MSG_00061" );
		map.put("0161", "COMMONM_MSG_00062" );
		map.put("0162", "COMMONM_MSG_00063" );
		map.put("0163", "COMMONM_MSG_00064" );
		map.put("0164", "COMMONM_MSG_00065" );
		map.put("0165", "COMMONM_MSG_00066" );
		map.put("0166", "COMMONM_MSG_00067" );
		map.put("0167", "COMMONM_MSG_00068" );
		map.put("0168", "COMMONM_MSG_00069" );
		map.put("0169", "COMMONM_MSG_00070" );
		map.put("0170", "COMMONM_MSG_00071" );
		map.put("0171", "COMMONM_MSG_00072" );
		map.put("0172", "COMMONM_MSG_00073" );
		map.put("0173", "COMMONM_MSG_00074" );
		map.put("0174", "COMMONM_MSG_00075" );
		map.put("0175", "COMMONM_MSG_00076" );
		map.put("0176", "COMMONM_MSG_00077" );
		map.put("0177", "COMMONM_MSG_00078" );
		map.put("0178", "COMMONM_MSG_00079" );
		map.put("0179", "COMMONM_MSG_00080" );
		map.put("0180", "COMMONM_MSG_00081" );
//		map.put("0181", "PLU送信日が有効日より以降の日付を指定してください。");
		map.put("0181", "COMMONM_MSG_00082" );
		map.put("0182", "COMMONM_MSG_00083" );
		map.put("0183", "COMMONM_MSG_00084" );
		map.put("0184", "COMMONM_MSG_00085" );
		map.put("0185", "COMMONM_MSG_00086" );
		map.put("0186", "COMMONM_MSG_00087" );
		map.put("0187", "COMMONM_MSG_00088" );
		map.put("0188", "COMMONM_MSG_00089" );
		map.put("0189", "COMMONM_MSG_00090" );
		map.put("0190", "COMMONM_MSG_00091" );
		map.put("0191", "COMMONM_MSG_00092" );
		map.put("0192", "COMMONM_MSG_00093" );
		map.put("0193", "COMMONM_MSG_00094" );
		map.put("0194", "COMMONM_MSG_00095" );
		map.put("0195", "COMMONM_MSG_00096" );
		map.put("0196", "COMMONM_MSG_00097" );
		map.put("0197", "COMMONM_MSG_00098" );
		map.put("0198", "COMMONM_MSG_00099" );
		map.put("0199", "COMMONM_MSG_00100" );
		map.put("0200", "COMMONM_MSG_00101" );
		map.put("0201", "COMMONM_MSG_00102" );
		map.put("0202", "COMMONM_MSG_00103" );
		map.put("0203", "COMMONM_MSG_00104" );
		map.put("0204", "COMMONM_MSG_00105" );
		map.put("0205", "COMMONM_MSG_00106" );
		map.put("0206", "COMMONM_MSG_00107" );
		map.put("0207", "COMMONM_MSG_00108" );
		map.put("0208", "COMMONM_MSG_00109" );
		map.put("0209", "COMMONM_MSG_00110" );
		map.put("0210", "COMMONM_MSG_00111" );
		map.put("0211", "COMMONM_MSG_00112" );
		map.put("0212", "COMMONM_MSG_00113" );
		map.put("0213", "COMMONM_MSG_00114" );
		map.put("0214", "COMMONM_MSG_00115" );
		map.put("0215", "COMMONM_MSG_00116" );
		map.put("0216", "COMMONM_MSG_00117" );
		map.put("0217", "COMMONM_MSG_00118" );
		map.put("0218", "COMMONM_MSG_00119" );
		map.put("0219", "COMMONM_MSG_00120" );
		map.put("0220", "COMMONM_MSG_00121" );
		map.put("0221", "COMMONM_MSG_00122" );
		map.put("0222", "COMMONM_MSG_00123" );
		map.put("0223", "COMMONM_MSG_00124" );
		map.put("0224", "COMMONM_MSG_00125" );
		map.put("0225", "COMMONM_MSG_00126" );
		map.put("0226", "COMMONM_MSG_00127" );
		map.put("0227", "COMMONM_MSG_00128" );
		map.put("0228", "COMMONM_MSG_00129" );
		map.put("0262", "COMMONM_MSG_00130" );
		map.put("0229", "COMMONM_MSG_00131" );
		map.put("0230", "COMMONM_MSG_00132" );
		map.put("0263", "COMMONM_MSG_00133" );
		map.put("0264", "COMMONM_MSG_00134" );
		map.put("0265", "COMMONM_MSG_00135" );
		map.put("0266", "COMMONM_MSG_00136" );// 2008/09/18 T.Yokoyama add
		map.put("0231", "COMMONM_MSG_00137");
		map.put("0232", "COMMONM_MSG_00138");
		map.put("0233", "COMMONM_MSG_00139");
		map.put("0234", "COMMONM_MSG_00140");
		map.put("0235", "COMMONM_MSG_00141");
//		map.put("0236", "同一商品コードの削除予定データが登録されている為、同一商品の部門切替以外は登録はできません。");
		map.put("0236", "COMMONM_MSG_00142");
		map.put("0237", "COMMONM_MSG_00143");
		map.put("0238", "COMMONM_MSG_00144");
		map.put("0239", "COMMONM_MSG_00145");
		map.put("0240", "COMMONM_MSG_00146");
		map.put("0241", "COMMONM_MSG_00147");
		map.put("0250", "COMMONM_MSG_00148");
		map.put("0251", "COMMONM_MSG_00149");
//		map.put("0261", "指定された物流区分が存在しません。");
		map.put("0260", "COMMONM_MSG_00150");
		map.put("0261", "COMMONM_MSG_00151");
		map.put("0400", "COMMONM_MSG_00152");
		map.put("0401", "COMMONM_MSG_00153");
		map.put("0402", "COMMONM_MSG_00154");
		map.put("0403", "COMMONM_MSG_00155");
		map.put("0404", "COMMONM_MSG_00156");
		map.put("0405", "COMMONM_MSG_00157");
		map.put("0406", "COMMONM_MSG_00158");
		map.put("0407", "COMMONM_MSG_00159");
		map.put("0408", "COMMONM_MSG_00160");
		map.put("0409", "COMMONM_MSG_00161");
		map.put("0410", "COMMONM_MSG_00162");
		map.put("0411", "COMMONM_MSG_00163");
		map.put("0412", "COMMONM_MSG_00164");
		map.put("0413", "COMMONM_MSG_00165");
		map.put("0414", "COMMONM_MSG_00166");
		map.put("0415", "COMMONM_MSG_00167");
		map.put("0416", "COMMONM_MSG_00168");
		map.put("0417", "COMMONM_MSG_00169");
		map.put("0418", "COMMONM_MSG_00170");
		map.put("0419", "COMMONM_MSG_00171");
		map.put("0420", "COMMONM_MSG_00172");
		map.put("0421", "COMMONM_MSG_00173");
		map.put("0422", "COMMONM_MSG_00174");
		map.put("0423", "COMMONM_MSG_00175");
		map.put("0424", "COMMONM_MSG_00176");
		map.put("0425", "COMMONM_MSG_00177");
		map.put("0426", "COMMONM_MSG_00178");
		map.put("0427", "COMMONM_MSG_00179");
		map.put("0428", "COMMONM_MSG_00180");
		map.put("0429", "COMMONM_MSG_00181");
		map.put("0430", "COMMONM_MSG_00182");
		map.put("0431", "COMMONM_MSG_00183");
		map.put("0432", "COMMONM_MSG_00184");
		map.put("0433", "COMMONM_MSG_00185");
		map.put("0434", "COMMONM_MSG_00186");
		map.put("0435", "COMMONM_MSG_00187");
		map.put("0436", "COMMONM_MSG_00188");
		map.put("0437", "COMMONM_MSG_00189");
		map.put("0438", "COMMONM_MSG_00190");
		map.put("0439", "COMMONM_MSG_00191");
		map.put("0440", "COMMONM_MSG_00192");
		map.put("0604", "COMMONM_MSG_00193");
		map.put("0605", "COMMONM_MSG_00194");
		map.put("0606", "COMMONM_MSG_00195");
		map.put("0607", "COMMON_MSG_00380");
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
		map.put("0627", "COMMON_MSG_00310");
//2011.02.25 Y.Imai Add 計量器保存温度帯区分追加対応 MM00111 Start
//2013.05.06 [MSTC00001] メーカコード コード管理対応 (S)
		map.put("0629", "COMMONM_MSG_00196");
		map.put("0630", "COMMONM_MSG_00197");
		map.put("0631", "COMMON_MSG_00011");
//2013.05.06 [MSTC00001] メーカコード コード管理対応 (E)
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (S)
		map.put("0632", "COMMONM_MSG_00198");
//2014.01.23 [CUS00105]  売価変更フラグ対応（画面側） (E)
		// 2017/03/15 T.Arimoto #4358対応（S)
		map.put("0700", "COMMON_MSG_00240");
		map.put("0701", "COMMON_MSG_00242");
		map.put("0702", "COMMONM_MSG_00199");
		map.put("0703", "COMMONM_MSG_00200");
		// 2017/03/15 T.Arimoto #4358対応（E)
		// 2017/10/04 T.Arimoto #5994対応 (S)
		map.put("0704", "COMMONM_MSG_00201");
		// 2017/10/04 T.Arimoto #5994対応 (E)
		// 2017/10/06 T.Arimoto #5994対応 (S)
		map.put("0705", "COMMONM_MSG_00201");
		map.put("0706", "COMMONM_MSG_00201");
		// 2017/10/06 T.Arimoto #5994対応 (E)

		return map;
	}

}
