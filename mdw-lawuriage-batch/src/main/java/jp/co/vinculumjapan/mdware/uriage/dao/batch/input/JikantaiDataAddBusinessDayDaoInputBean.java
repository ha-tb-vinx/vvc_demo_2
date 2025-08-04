package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * 
 * <p>タイトル: JikantaiDataAddBusinessDayDaoInputBean クラス</p>
 * <p>説明　: 時間帯データ営業日付加入力Bean</p>
 * <p>著作権: Copyright (c) 2016</p>
 * <p>会社名: VINX</p>
 * @Version 1.00 (2016.05.20) T.Kamei 新規作成
 *
 */
public class JikantaiDataAddBusinessDayDaoInputBean {
    
    /* 入力フォルダ */
    private String inputFilePath = null;
    
    /* 出力フォルダ */
    private String outputFilePath = null;

    /**
     * 入力ファイルパスを取得する
     * 
     * @return String
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * 入力ファイルパスを設定する
     * 
     * @param String inputFilePath
     */
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * 出力ファイルパスを取得する
     * 
     * @return String
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * 出力ファイルパスを設定する
     * 
     * @param String outputFilePath
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

}
