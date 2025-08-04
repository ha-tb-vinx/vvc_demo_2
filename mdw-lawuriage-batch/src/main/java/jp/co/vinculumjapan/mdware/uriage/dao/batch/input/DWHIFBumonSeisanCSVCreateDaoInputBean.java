package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: DWHIFBumonSeisanCSVCreateDaoInputBean クラス</p>
 * <p>説明: DWHIFBumonSeisanCSVCreateDaoの入力用Bean</p>
 *  <P>著作権: Copyright (c) 2013</p>
 *  <P>会社名: VINX</P>
 *  @author T.Morihiro
 *  @version 3.0  (2013.10.09) T.Morihiro [CUS00057] ランドローム様対応 売上管理―URIB131_日別売上集計処理
 */
public class DWHIFBumonSeisanCSVCreateDaoInputBean {

    /** 外部ファイル名 */
    private String outsideFileName = null;
    
    
    /**
     * 外部ファイル名 を返します
     * @return outsideFileName
     */
    public String getOutsideFileName() {
        return outsideFileName;
    }

    /**
     * 外部ファイル名 を設定します
     * @param outsideFileName 設定する outsideFileName
     */
    public void setOutsideFileName(String outsideFileName) {
        this.outsideFileName = outsideFileName;
    }

}
