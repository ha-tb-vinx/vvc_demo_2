package jp.co.vinculumjapan.mdware.uriage.dao.batch.input;

/**
 * <p>タイトル: OutsideDayTenDPTSeisanDataCreateDaoInputBean クラス</p>
 * <p>説明: OutsideDayTenDPTSeisanDataCreateDaoの入力用Bean</p>
 * <p>著作権: Copyright (c) 2009</p>
 * <p>会社名: </p>
 * @author S.Hamaguchi
 * @version 1.00 (2009/09/29)
 */
public class OutsideDayTenDPTSeisanDataCreateDaoInputBean {

    /** 外部ファイル名 */
    private String outsideFileName = null;
    
    /** 関連会社用法人コード */
    private String kanCompCd = null;

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

    /**
     * 関連会社用法人コード を返します
     * @return kanCompCd
     */
    public String getKanCompCd() {
        return kanCompCd;
    }

    /**
     * 関連会社用法人コード を設定します
     * @param kanCompCd 設定する kanCompCd
     */
    public void setKanCompCd(String kanCompCd) {
        this.kanCompCd = kanCompCd;
    }
}
