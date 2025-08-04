package mdware.common.dictionary;

/**
 * <p>タイトル: RB Site</p>
 * <p>説明: 宛先選択機能用送信者の「利用ユーザ選択区分」(see 「メッセージBOXサブシステム仕様書（画面）」1.4.1.2.1(2))</p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author nob
 * @version 1.0
 */
import java.util.Map;
import java.util.HashMap;

public class SendTargetClassification {
    private final String code;
    private final String name;
    private static Map map = new HashMap();

    public SendTargetClassification(String code, String name) {
        this.code = code;
        this.name = name;
        map.put(code, this);
    }

    public String toString() { return name; }
    public String getCode() { return code; }

    public static SendTargetClassification getSendTargetClassification(String code) {
        SendTargetClassification sendTargetClassification = (SendTargetClassification)map.get(code);

        if (sendTargetClassification == null)
            return UNKNOWN;

        return sendTargetClassification;
    }

    public static final SendTargetClassification USER = new SendTargetClassification("0", "ユーザ単位に送信");
    public static final SendTargetClassification DEALER = new SendTargetClassification("1", "取引先単位に送信");
    public static final SendTargetClassification RETAIL = new SendTargetClassification("2", "小売単位に送信");
    public static final SendTargetClassification SEND = new SendTargetClassification("3", "送信先単位に送信");
    public static final SendTargetClassification UNKNOWN = new SendTargetClassification("X", "送信単位不明");
}
