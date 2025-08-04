package mdware.common.util.properties;

import jp.co.vinculumjapan.stc.util.properties.StcLibProperty;
import mdware.common.batch.util.properties.BatchProperty;

/**
 * <p>タイトル: PropertyUtil</p>
 * <p>説明: StcLibPropertyとBatchPropertyをまとめて管理するクラス</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan corporation</p>
 * @author S.Shimatani
 * @version 1.0.0 (2006/11/10) 初版作成
 */
public class PropertyUtil {
	
    public static String getPropertiesDir() {
        if (BatchProperty.propertiesDir == null) {
			return StcLibProperty.propertiesDir;
        } else {
            return BatchProperty.propertiesDir;
        }
    }
}
