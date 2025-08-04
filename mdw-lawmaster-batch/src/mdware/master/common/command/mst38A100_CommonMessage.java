package mdware.master.common.command;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>タイトル:商品マスタ生成バッチ共通メッセージクラス</p>
 * <p>説明:共通のメッセージを保持するクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 @version 3.01 2022/01/04 HOAI.TTT #6409 対応
 * @author VVC
 */

public class mst38A100_CommonMessage {
	
	public static Map MsgMap = null;

	public static Map getMsg() {

		if (MsgMap == null) {
			MsgMap = createMap();
		}
		return MsgMap;
	}

	private static Map createMap() {
		
		Map map = new HashMap();
		

		map.put("0113", "BATCH_COMMONM_MSG_00014");
		map.put("0114", "BATCH_COMMONM_MSG_00015");
		map.put("0115", "BATCH_COMMONM_MSG_00016");
		map.put("0116", "BATCH_COMMONM_MSG_00017");
		map.put("0117", "BATCH_COMMONM_MSG_00018");
		map.put("0118", "BATCH_COMMONM_MSG_00019" );
		map.put("0119", "BATCH_COMMONM_MSG_00020" );
		map.put("0120", "BATCH_COMMONM_MSG_00021" );
		map.put("0121", "BATCH_COMMONM_MSG_00022" );
		map.put("0122", "BATCH_COMMONM_MSG_00023" );
		map.put("0123", "BATCH_COMMONM_MSG_00024" );
		map.put("0124", "BATCH_COMMONM_MSG_00025" );
		map.put("0125", "BATCH_COMMONM_MSG_00026" );
		map.put("0126", "BATCH_COMMONM_MSG_00027" );
		map.put("0127", "BATCH_COMMONM_MSG_00028" );
		map.put("0128", "BATCH_COMMONM_MSG_00029" );
		map.put("0129", "BATCH_COMMONM_MSG_00030" );
		map.put("0130", "BATCH_COMMONM_MSG_00031" );
		map.put("0131", "BATCH_COMMONM_MSG_00032" );
		map.put("0132", "BATCH_COMMONM_MSG_00033" );
		map.put("0133", "BATCH_COMMONM_MSG_00034" );
		map.put("0134", "BATCH_COMMONM_MSG_00035" );
		map.put("0135", "BATCH_COMMONM_MSG_00036" );
		map.put("0136", "BATCH_COMMONM_MSG_00037" );
		map.put("0137", "BATCH_COMMONM_MSG_00038" );
		map.put("0138", "BATCH_COMMONM_MSG_00039" );
		map.put("0139", "BATCH_COMMONM_MSG_00040" );
		map.put("0140", "BATCH_COMMONM_MSG_00041" );
		map.put("0141", "BATCH_COMMONM_MSG_00042" );
		map.put("0142", "BATCH_COMMONM_MSG_00043" );
		map.put("0143", "BATCH_COMMONM_MSG_00044" );
		map.put("0144", "BATCH_COMMONM_MSG_00045" );
		map.put("0145", "BATCH_COMMONM_MSG_00046" );
		map.put("0146", "BATCH_COMMONM_MSG_00047" );
		map.put("0147", "BATCH_COMMONM_MSG_00048" );
		map.put("0148", "BATCH_COMMONM_MSG_00049" );
		map.put("0149", "BATCH_COMMONM_MSG_00050" );
		map.put("0150", "BATCH_COMMONM_MSG_00051" );
		map.put("0151", "BATCH_COMMONM_MSG_00052" );
		map.put("0152", "BATCH_COMMONM_MSG_00053" );
		map.put("0153", "BATCH_COMMONM_MSG_00054" );
		map.put("0154", "BATCH_COMMONM_MSG_00055" );
		map.put("0155", "BATCH_COMMONM_MSG_00056" );
		map.put("0156", "BATCH_COMMONM_MSG_00057" );
		map.put("0157", "BATCH_COMMONM_MSG_00058" );
		map.put("0158", "BATCH_COMMONM_MSG_00059" );
		map.put("0159", "BATCH_COMMONM_MSG_00060" );
		map.put("0160", "BATCH_COMMONM_MSG_00061" );
		map.put("0161", "BATCH_COMMONM_MSG_00062" );
		map.put("0162", "BATCH_COMMONM_MSG_00063" );
		map.put("0163", "BATCH_COMMONM_MSG_00064" );
		map.put("0164", "BATCH_COMMONM_MSG_00065" );
		map.put("0165", "BATCH_COMMONM_MSG_00066" );
		map.put("0166", "BATCH_COMMONM_MSG_00067" );
		map.put("0167", "BATCH_COMMONM_MSG_00068" );
		map.put("0168", "BATCH_COMMONM_MSG_00069" );
		map.put("0169", "BATCH_COMMONM_MSG_00070" );
		map.put("0170", "BATCH_COMMONM_MSG_00071" );
		map.put("0171", "BATCH_COMMONM_MSG_00072" );
		map.put("0172", "BATCH_COMMONM_MSG_00073" );
		map.put("0173", "BATCH_COMMONM_MSG_00074" );
		map.put("0174", "BATCH_COMMONM_MSG_00075" );
		map.put("0175", "BATCH_COMMONM_MSG_00076" );
		map.put("0176", "BATCH_COMMONM_MSG_00077" );
		map.put("0177", "BATCH_COMMONM_MSG_00078" );
		map.put("0178", "BATCH_COMMONM_MSG_00079" );
		map.put("0179", "BATCH_COMMONM_MSG_00080" );
		map.put("0180", "BATCH_COMMONM_MSG_00081" );
		map.put("0181", "BATCH_COMMONM_MSG_00082" );
		map.put("0182", "BATCH_COMMONM_MSG_00083" );
		map.put("0183", "BATCH_COMMONM_MSG_00084" );
		map.put("0184", "BATCH_COMMONM_MSG_00085" );
		map.put("0185", "BATCH_COMMONM_MSG_00086" );
		map.put("0186", "BATCH_COMMONM_MSG_00087" );
		map.put("0187", "BATCH_COMMONM_MSG_00088" );
		map.put("0188", "BATCH_COMMONM_MSG_00089" );
		map.put("0189", "BATCH_COMMONM_MSG_00090" );
		map.put("0190", "BATCH_COMMONM_MSG_00091" );
		map.put("0191", "BATCH_COMMONM_MSG_00092" );
		map.put("0192", "BATCH_COMMONM_MSG_00093" );
		map.put("0193", "BATCH_COMMONM_MSG_00094" );
		map.put("0194", "BATCH_COMMONM_MSG_00095" );
		map.put("0195", "BATCH_COMMONM_MSG_00096" );
		map.put("0196", "BATCH_COMMONM_MSG_00097" );
		map.put("0197", "BATCH_COMMONM_MSG_00098" );
		map.put("0198", "BATCH_COMMONM_MSG_00099" );
		map.put("0199", "BATCH_COMMONM_MSG_00100" );
		map.put("0200", "BATCH_COMMONM_MSG_00101" );
		map.put("0201", "BATCH_COMMONM_MSG_00102" );
		map.put("0202", "BATCH_COMMONM_MSG_00103" );
		map.put("0203", "BATCH_COMMONM_MSG_00104" );
		map.put("0204", "BATCH_COMMONM_MSG_00105" );
		map.put("0205", "BATCH_COMMONM_MSG_00106" );
		map.put("0206", "BATCH_COMMONM_MSG_00107" );
		map.put("0207", "BATCH_COMMONM_MSG_00108" );
		map.put("0208", "BATCH_COMMONM_MSG_00109" );
		map.put("0209", "BATCH_COMMONM_MSG_00110" );
		map.put("0210", "BATCH_COMMONM_MSG_00111" );
		map.put("0211", "BATCH_COMMONM_MSG_00112" );
		map.put("0212", "BATCH_COMMONM_MSG_00113" );
		map.put("0213", "BATCH_COMMONM_MSG_00114" );
		map.put("0214", "BATCH_COMMONM_MSG_00115" );
		map.put("0215", "BATCH_COMMONM_MSG_00116" );
		map.put("0216", "BATCH_COMMONM_MSG_00117" );
		map.put("0217", "BATCH_COMMONM_MSG_00118" );
		map.put("0218", "BATCH_COMMONM_MSG_00119" );
		map.put("0219", "BATCH_COMMONM_MSG_00120" );
		map.put("0220", "BATCH_COMMONM_MSG_00121" );
		map.put("0221", "BATCH_COMMONM_MSG_00122" );
		map.put("0222", "BATCH_COMMONM_MSG_00123" );
		map.put("0223", "BATCH_COMMONM_MSG_00124" );
		map.put("0224", "BATCH_COMMONM_MSG_00125" );
		map.put("0225", "BATCH_COMMONM_MSG_00126" );
		map.put("0226", "BATCH_COMMONM_MSG_00127" );
		map.put("0227", "BATCH_COMMONM_MSG_00128" );
		map.put("0228", "BATCH_COMMONM_MSG_00129" );
		map.put("0262", "BATCH_COMMONM_MSG_00130" );
		map.put("0229", "BATCH_COMMONM_MSG_00131" );
		map.put("0230", "BATCH_COMMONM_MSG_00132" );
		map.put("0263", "BATCH_COMMONM_MSG_00133" );
		map.put("0264", "BATCH_COMMONM_MSG_00134" );
		map.put("0265", "BATCH_COMMONM_MSG_00135" );
		map.put("0266", "BATCH_COMMONM_MSG_00136" );
		map.put("0231", "BATCH_COMMONM_MSG_00137");
		map.put("0232", "BATCH_COMMONM_MSG_00138");
		map.put("0233", "BATCH_COMMONM_MSG_00139");
		map.put("0234", "BATCH_COMMONM_MSG_00140");
		map.put("0235", "BATCH_COMMONM_MSG_00141");
		map.put("0236", "BATCH_COMMONM_MSG_00142");
		map.put("0237", "BATCH_COMMONM_MSG_00143");
		map.put("0238", "BATCH_COMMONM_MSG_00144");
		map.put("0239", "BATCH_COMMONM_MSG_00145");
		map.put("0240", "BATCH_COMMONM_MSG_00146");
		map.put("0241", "BATCH_COMMONM_MSG_00147");
		map.put("0250", "BATCH_COMMONM_MSG_00148");
		map.put("0251", "BATCH_COMMONM_MSG_00149");
		map.put("0260", "BATCH_COMMONM_MSG_00150");
		map.put("0261", "BATCH_COMMONM_MSG_00151");
		map.put("0400", "BATCH_COMMONM_MSG_00152");
		map.put("0401", "BATCH_COMMONM_MSG_00153");
		map.put("0402", "BATCH_COMMONM_MSG_00154");
		map.put("0403", "BATCH_COMMONM_MSG_00155");
		map.put("0404", "BATCH_COMMONM_MSG_00156");
		map.put("0405", "BATCH_COMMONM_MSG_00157");
		map.put("0406", "BATCH_COMMONM_MSG_00158");
		map.put("0407", "BATCH_COMMONM_MSG_00159");
		map.put("0408", "BATCH_COMMONM_MSG_00160");
		map.put("0409", "BATCH_COMMONM_MSG_00161");
		map.put("0410", "BATCH_COMMONM_MSG_00162");
		map.put("0411", "BATCH_COMMONM_MSG_00163");
		map.put("0412", "BATCH_COMMONM_MSG_00164");
		map.put("0413", "BATCH_COMMONM_MSG_00165");
		map.put("0414", "BATCH_COMMONM_MSG_00166");
		map.put("0415", "BATCH_COMMONM_MSG_00167");
		map.put("0416", "BATCH_COMMONM_MSG_00168");
		map.put("0417", "BATCH_COMMONM_MSG_00169");
		map.put("0418", "BATCH_COMMONM_MSG_00170");
		map.put("0419", "BATCH_COMMONM_MSG_00171");
		map.put("0420", "BATCH_COMMONM_MSG_00172");
		map.put("0421", "BATCH_COMMONM_MSG_00173");
		map.put("0422", "BATCH_COMMONM_MSG_00174");
		map.put("0423", "BATCH_COMMONM_MSG_00175");
		map.put("0424", "BATCH_COMMONM_MSG_00176");
		map.put("0425", "BATCH_COMMONM_MSG_00177");
		map.put("0426", "BATCH_COMMONM_MSG_00178");
		map.put("0427", "BATCH_COMMONM_MSG_00179");
		map.put("0428", "BATCH_COMMONM_MSG_00180");
		map.put("0429", "BATCH_COMMONM_MSG_00181");
		map.put("0430", "BATCH_COMMONM_MSG_00182");
		map.put("0431", "BATCH_COMMONM_MSG_00183");
		map.put("0432", "BATCH_COMMONM_MSG_00184");
		map.put("0433", "BATCH_COMMONM_MSG_00185");
		map.put("0434", "BATCH_COMMONM_MSG_00186");
		map.put("0435", "BATCH_COMMONM_MSG_00187");
		map.put("0436", "BATCH_COMMONM_MSG_00188");
		map.put("0437", "BATCH_COMMONM_MSG_00189");
		map.put("0438", "BATCH_COMMONM_MSG_00190");
		map.put("0439", "BATCH_COMMONM_MSG_00191");
		map.put("0440", "BATCH_COMMONM_MSG_00192");
		map.put("0604", "BATCH_COMMONM_MSG_00193");
		map.put("0605", "BATCH_COMMONM_MSG_00194");
		map.put("0606", "BATCH_COMMONM_MSG_00195");
		map.put("0607", "BATCH_COMMON_MSG_00380");
		map.put("0627", "BATCH_COMMON_MSG_00310");
		map.put("0629", "BATCH_COMMONM_MSG_00196");
		map.put("0630", "BATCH_COMMONM_MSG_00197");
		map.put("0631", "BATCH_COMMON_MSG_00011");
		map.put("0632", "BATCH_COMMONM_MSG_00198");
		map.put("0700", "BATCH_COMMON_MSG_00240");
		map.put("0701", "BATCH_COMMON_MSG_00242");
		map.put("0702", "BATCH_COMMONM_MSG_00199");
		map.put("0703", "BATCH_COMMONM_MSG_00200");
		map.put("0704", "BATCH_COMMONM_MSG_00201");
		map.put("0705", "BATCH_COMMONM_MSG_00201");
		map.put("0706", "BATCH_COMMONM_MSG_00201");
		
		return map;
	}
}
