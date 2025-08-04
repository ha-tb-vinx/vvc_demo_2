package mdware.common.bean;

import java.util.*;
import java.sql.*;
import jp.co.vinculumjapan.stc.log.StcLog;
import jp.co.vinculumjapan.stc.util.servlet.HTMLStringUtil;
import jp.co.vinculumjapan.stc.util.bean.BeanHolder;
import jp.co.vinculumjapan.stc.util.servlet.DataHolder;
import jp.co.vinculumjapan.stc.util.calendar.DateChanger;

/**
 * <p>タイトル: </p>
 * <p>説明: </p>
 * <p>著作権: Copyright (c) 2002</p>
 * <p>会社名: </p>
 * @author VINX
 * @version 1.0 2014/06/23 Nghia-HT 海外LAWSON様UTF-8対応
 */
public class DenpyoNbControlBean {
    private static final StcLog stcLog = StcLog.getInstance();

    public static final int DENPYO_SYUBETU_KB_MAX_LENGTH = 2;
    public static final int RB_SAIBAN_SYUBETU_KB_MAX_LENGTH = 10;
    public static final int NOW_NB_MAX_LENGTH = 10;
    public static final int BEFORE_NB_MAX_LENGTH = 10;

    private String denpyo_syubetu_kb = null;
    private String rb_saiban_syubetu_kb = null;
    private String now_nb = null;
    private String before_nb = null;
    private int denpyo_len = 0;

    public DenpyoNbControlBean(ResultSet rest) throws SQLException {
        this.setDenpyoSyubetuKb(rest.getString("denpyo_syubetu_kb"));
        this.setRbSaibanSyubetuKb(rest.getString("rb_saiban_syubetu_kb"));
        this.setNowNb(rest.getString("now_nb"));
        this.setBeforeNb(rest.getString("before_nb"));
        this.setDenpyoLen(rest.getInt("denpyo_len"));
    }
    // DBから抽出したBeanかどうかを保持する。主にＤＢ更新を行う時に役に立つフラグ。
    private boolean createDatabase = false;
    protected void setCreateDatabase() {
        createDatabase = true;
    }
    public boolean isCreateDatabase() {
        return createDatabase;
    }

    // denpyo_syubetu_kbに対するセッターとゲッターの集合
    public boolean setDenpyoSyubetuKb(String denpyo_syubetu_kb) {
        this.denpyo_syubetu_kb = denpyo_syubetu_kb;
        return true;
    }
    public String getDenpyoSyubetuKb() {
        return cutString(this.denpyo_syubetu_kb, DENPYO_SYUBETU_KB_MAX_LENGTH);
    }
    public String getDenpyoSyubetuKbString() {
        return "'" + cutString(this.denpyo_syubetu_kb, DENPYO_SYUBETU_KB_MAX_LENGTH) + "'";
    }
    public String getDenpyoSyubetuKbHTMLString() {
        return HTMLStringUtil.convert(cutString(this.denpyo_syubetu_kb, DENPYO_SYUBETU_KB_MAX_LENGTH));
    }

    // rb_saiban_syubetu_kbに対するセッターとゲッターの集合
    public boolean setRbSaibanSyubetuKb(String rb_saiban_syubetu_kb) {
        this.rb_saiban_syubetu_kb = rb_saiban_syubetu_kb;
        return true;
    }
    public String getRbSaibanSyubetuKb() {
        return cutString(this.rb_saiban_syubetu_kb, RB_SAIBAN_SYUBETU_KB_MAX_LENGTH);
    }
    public String getRbSaibanSyubetuKbString() {
        return "'" + cutString(this.rb_saiban_syubetu_kb, RB_SAIBAN_SYUBETU_KB_MAX_LENGTH) + "'";
    }
    public String getRbSaibanSyubetuKbHTMLString() {
        return HTMLStringUtil.convert(cutString(this.rb_saiban_syubetu_kb, RB_SAIBAN_SYUBETU_KB_MAX_LENGTH));
    }

    // now_nbに対するセッターとゲッターの集合
    public boolean setNowNb(String now_nb) {
        this.now_nb = now_nb;
        return true;
    }
    public String getNowNb() {
        return cutString(this.now_nb, NOW_NB_MAX_LENGTH);
    }
    public String getNowNbString() {
        return "'" + cutString(this.now_nb, NOW_NB_MAX_LENGTH) + "'";
    }
    public String getNowNbHTMLString() {
        return HTMLStringUtil.convert(cutString(this.now_nb, NOW_NB_MAX_LENGTH));
    }

    // before_nbに対するセッターとゲッターの集合
    public boolean setBeforeNb(String before_nb) {
        this.before_nb = before_nb;
        return true;
    }
    public String getBeforeNb() {
        return cutString(this.before_nb, BEFORE_NB_MAX_LENGTH);
    }
    public String getBeforeNbString() {
        return "'" + cutString(this.before_nb, BEFORE_NB_MAX_LENGTH) + "'";
    }
    public String getBeforeNbHTMLString() {
        return HTMLStringUtil.convert(cutString(this.before_nb, BEFORE_NB_MAX_LENGTH));
    }

    // denpyo_lenに対するセッターとゲッターの集合
    public boolean setDenpyoLen(String denpyo_len) {
        try {
            this.denpyo_len = Integer.valueOf(denpyo_len).intValue();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean setDenpyoLen(int denpyo_len) {
        this.denpyo_len = denpyo_len;
        return true;
    }
    public int getDenpyoLen() {
        return this.denpyo_len;
    }
    public String getDenpyoLenString() {
        return Double.toString(this.denpyo_len);
    }
    /**
     * ObjectのtoStringをオーバーライドする。
     * 内容全てをフラットな文字列する。
     * @return String このクラスの全ての内容を文字列にし返す。
     */
    public String toString() {
        return "  denpyo_syubetu_kb = "
            + getDenpyoSyubetuKbString()
            + "  rb_saiban_syubetu_kb = "
            + getRbSaibanSyubetuKbString()
            + "  now_nb = "
            + getNowNbString()
            + "  before_nb = "
            + getBeforeNbString()
            + "  denpyo_len = "
            + getDenpyoLenString()
            + " createDatabase  = "
            + createDatabase;
    }
    /**
     * Objectのequalsをオーバーライドする。
     * 内容がまったく同じかどうかを返す。
     * @param Object 比較を行う対象
     * @return boolean 比較対照がnull,内容が違う時はfalseを返す。
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof DenpyoNbControlBean))
            return false;
        return this.toString().equals(o.toString());
    }
    /**
     * 文字列を最大バイト数で判断しはみ出した部分を削除する。
     * このとき全角の半端な場所になる時はその文字の前でカットされる。
     * @param String カットされる文字列
     * @param int 最大バイト数
     * @return String カット後の文字列
     */
    private String cutString(String base, int max) {
        if (base == null)
            return null;
        String wk = "";
        for (int pos = 0, count = 0; pos < max && pos < base.length(); pos++) {
            try {
                byte bt[] = base.substring(pos, pos + 1).getBytes("UTF-8");
                count += bt.length;
                if (count > max)
                    break;
                wk += base.substring(pos, pos + 1);
            } catch (Exception e) {
                stcLog.getLog().debug(
                    this.getClass().getName()
                        + "/cutString/"
                        + base
                        + "/"
                        + base.substring(pos, pos + 1)
                        + "をShift_JISに変換できませんでした。");
            }
        }
        return wk;
    }
}
