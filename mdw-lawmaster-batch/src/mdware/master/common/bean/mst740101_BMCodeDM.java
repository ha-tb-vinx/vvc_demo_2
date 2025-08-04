/**
 * <p>タイトル: 新ＭＤシステム（マスター管理）BMコードマスタのDMクラス</p>
 * <p>説明: 新ＭＤシステムで使用するBMコードマスタのDMクラス(DmCreatorにより自動生成)</p>
 * <p>著作権: Copyright (c) 2005</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/19)初版作成
 */
package mdware.master.common.bean;

import java.sql.*;
import java.util.*;

import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import jp.co.vinculumjapan.stc.util.db.*;

/**
 * <p>
 * タイトル: 新ＭＤシステム（マスター管理）BMコードマスタのDMクラス
 * </p>
 * <p>
 * 説明: 新ＭＤシステムで使用するBMコードマスタのDMクラス(DmCreatorにより自動生成)
 * </p>
 * <p>
 * 著作権: Copyright (c) 2005
 * </p>
 * <p>
 * 会社名: Vinculum Japan Corp.
 * </p>
 * 
 * @author FSIABC E.Yoshikawa
 * @version 1.0(2005/04/19)初版作成
 */
public class mst740101_BMCodeDM extends DataModule {
	private DBStringConvert conv = DBStringConvert.getDBStringConvert( getDatabaseProductName() );
    /**
     * コンストラクタ
     */
    public mst740101_BMCodeDM() {
        super(mst000101_ConstDictionary.CONNECTION_STR);
    }

    /**
     * 検索後にＢＥＡＮをインスタンス化する所。 検索した結果セットをＢＥＡＮとして持ち直す。
     * DataModuleから呼び出され返したObjectをListに追加する。
     * 
     * @param rest
     *            ResultSet
     * @return Object インスタンス化されたＢＥＡＮ
     */
    protected Object instanceBean(ResultSet rest) throws SQLException {
        mst740101_BMCodeBean bean = new mst740101_BMCodeBean();
        bean.setBundlemixCd(rest.getString("bundlemix_cd"));
        bean.setNameNa(rest.getString("name_na"));
        bean.setNameKa(rest.getString("name_ka"));
        bean.setBundlemixStDt(rest.getString("bundlemix_st_dt"));
        bean.setBundlemixEdDt(rest.getString("bundlemix_ed_dt"));
        bean.setBundlemixSttimeQt(rest.getString("bundlemix_sttime_qt"));
        bean.setBundlemixEdtimeQt(rest.getString("bundlemix_edtime_qt"));
        bean.setSeirituKosuQt(rest.getLong("seiritu_kosu_qt"));
        bean.setSeirituKingakuVl(rest.getLong("seiritu_kingaku_vl"));
        bean.setTankaVl(rest.getLong("tanka_vl"));
        bean.setJisiMonFg(rest.getString("jisi_mon_fg"));
        bean.setJisiTueFg(rest.getString("jisi_tue_fg"));
        bean.setJisiWedFg(rest.getString("jisi_wed_fg"));
        bean.setJisiThuFg(rest.getString("jisi_thu_fg"));
        bean.setJisiFriFg(rest.getString("jisi_fri_fg"));
        bean.setJisiSatFg(rest.getString("jisi_sat_fg"));
        bean.setJisiSunFg(rest.getString("jisi_sun_fg"));
        bean.setInsertTs(rest.getString("insert_ts"));
        bean.setInsertUserId(rest.getString("insert_user_id"));
        bean.setUpdateTs(rest.getString("update_ts"));
        bean.setUpdateUserId(rest.getString("update_user_id"));
        bean.setDeleteFg(rest.getString("delete_fg"));
        bean.setInsertUserNm(rest.getString("insert_user_nm"));
        bean.setUpdateUserNm(rest.getString("update_user_nm"));

		//A.Tozaki
		bean.setSinkiTorokuDt(rest.getString("sinki_toroku_dt"));
		bean.setHenkoDt(rest.getString("henko_dt"));

        bean.setCreateDatabase();
        return bean;
    }

    /**
     * 検索用ＳＱＬの生成を行う。 渡されたMapを元にWHERE区を作成する。
     * 
     * @param map
     *            Map
     * @return String 生成されたＳＱＬ
     */
    public String getSelectSql(Map map) {
//        DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
        StringBuffer sb = new StringBuffer();
        sb.append("select tbl1.* ");
        sb.append(", ");
        sb.append("tbl3.user_na as insert_user_nm ");
        sb.append(", ");
        sb.append("tbl4.user_na as update_user_nm ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//        sb.append("from R_BANDLEMIX_CODE tbl1");
		sb.append("from R_BUNDLEMIX_CD tbl1");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
        if (map.get("kikaku_tokubaino_cd") != null
                && ((String) map.get("kikaku_tokubaino_cd")).trim().length() != 0) {        
            sb.append(" ,R_BM_KIKAKUTOKUBAI_RENKAN tbl2 ");
        }
        
        sb.append(" ,SYS_SOSASYA tbl3 ");
        sb.append(" ,SYS_SOSASYA tbl4 ");

        sb.append("where ");
        
        if (map.get("bundlemix_cd") != null
                && ((String) map.get("bundlemix_cd")).trim().length() != 0) {
            sb.append("tbl1.bundlemix_cd = '"
                    + conv.convertWhereString((String) map
                            .get("bundlemix_cd")) + "' ");
        } 
        else {
            sb.append("tbl2.bundlemix_cd  = tbl1.bundlemix_cd ");            
        }

        sb.append("and ");
        sb.append("tbl3.user_id (+)= tbl1.insert_user_id ");
        sb.append("and ");
        sb.append("tbl4.user_id (+)= tbl1.update_user_id ");
		sb.append(" AND tbl3.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");
		sb.append(" AND tbl4.hojin_cd (+)='" +  mst000101_ConstDictionary.HOJIN_CD + "' ");

        if (map.get("delete_fg") != null
                && ((String) map.get("delete_fg")).trim().length() != 0) {
            sb.append("and ");
            sb.append("tbl1.delete_fg (+)= '"
                    + conv.convertWhereString((String) map.get("delete_fg"))
                    + "' ");
        }

        if (map.get("kikaku_tokubaino_cd") != null
                && ((String) map.get("kikaku_tokubaino_cd")).trim().length() != 0) {
            sb.append("and ");
            sb.append("tbl2.kikaku_tokubaino_cd = '"
                    + conv.convertWhereString((String) map
                            .get("kikaku_tokubaino_cd")) + "' ");
        }
        
        sb.append(" order by ");
        sb.append("tbl1.bundlemix_cd");
        return sb.toString();
    }

    /**
     * 挿入用ＳＱＬの生成を行う。 渡されたBEANをＤＢに挿入するためのＳＱＬ。
     * 
     * @param beanMst
     *            Object
     * @return String 生成されたＳＱＬ
     */
    public String getInsertSql(Object beanMst) {
        boolean befKanma = false;
        boolean aftKanma = false;
//        DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
        mst740101_BMCodeBean bean = (mst740101_BMCodeBean) beanMst;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//        sb.append("R_BANDLEMIX_CODE (");
		sb.append("R_BUNDLEMIX_CD (");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
        if (bean.getBundlemixCd() != null
                && bean.getBundlemixCd().trim().length() != 0) {
            befKanma = true;
            sb.append(" bundlemix_cd");
        }
        if (bean.getNameNa() != null && bean.getNameNa().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" name_na");
        }
        if (bean.getNameKa() != null && bean.getNameKa().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" name_ka");
        }
        if (bean.getBundlemixStDt() != null
                && bean.getBundlemixStDt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_st_dt");
        }
        if (bean.getBundlemixEdDt() != null
                && bean.getBundlemixEdDt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_ed_dt");
        }
        if (bean.getBundlemixSttimeQt() != null
                && bean.getBundlemixSttimeQt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_sttime_qt");
        }
        if (bean.getBundlemixEdtimeQt() != null
                && bean.getBundlemixEdtimeQt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_edtime_qt");
        }
        if (befKanma)
            sb.append(",");
        sb.append(" seiritu_kosu_qt");
        sb.append(",");
        sb.append(" seiritu_kingaku_vl");
        sb.append(",");
        sb.append(" tanka_vl");
        if (bean.getJisiMonFg() != null
                && bean.getJisiMonFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_mon_fg");
        }
        if (bean.getJisiTueFg() != null
                && bean.getJisiTueFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_tue_fg");
        }
        if (bean.getJisiWedFg() != null
                && bean.getJisiWedFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_wed_fg");
        }
        if (bean.getJisiThuFg() != null
                && bean.getJisiThuFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_thu_fg");
        }
        if (bean.getJisiFriFg() != null
                && bean.getJisiFriFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_fri_fg");
        }
        if (bean.getJisiSatFg() != null
                && bean.getJisiSatFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_sat_fg");
        }
        if (bean.getJisiSunFg() != null
                && bean.getJisiSunFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_sun_fg");
        }
        if (bean.getInsertTs() != null
                && bean.getInsertTs().trim().length() != 0) {
            sb.append(",");
            sb.append(" insert_ts");
        }
        if (bean.getInsertUserId() != null
                && bean.getInsertUserId().trim().length() != 0) {
            sb.append(",");
            sb.append(" insert_user_id");
        }
        if (bean.getUpdateTs() != null
                && bean.getUpdateTs().trim().length() != 0) {
            sb.append(",");
            sb.append(" update_ts");
        }
        if (bean.getUpdateUserId() != null
                && bean.getUpdateUserId().trim().length() != 0) {
            sb.append(",");
            sb.append(" update_user_id");
        }
        if (bean.getDeleteFg() != null
                && bean.getDeleteFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" delete_fg");
        }


		//A.Tozaki
		if (bean.getSinkiTorokuDt() != null
				&& bean.getSinkiTorokuDt().trim().length() != 0) {
			sb.append(",");
			sb.append(" sinki_toroku_dt");
		}
		if (bean.getHenkoDt() != null
				&& bean.getHenkoDt().trim().length() != 0) {
			sb.append(",");
			sb.append(" henko_dt");
		}
		

        sb.append(")Values(");

        if (bean.getBundlemixCd() != null
                && bean.getBundlemixCd().trim().length() != 0) {
            aftKanma = true;
            sb.append("'" + conv.convertString(bean.getBundlemixCd()) + "'");
        }
        if (bean.getNameNa() != null && bean.getNameNa().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getNameNa()) + "'");
        }
        if (bean.getNameKa() != null && bean.getNameKa().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getNameKa()) + "'");
        }
        if (bean.getBundlemixStDt() != null
                && bean.getBundlemixStDt().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getBundlemixStDt()) + "'");
        }
        if (bean.getBundlemixEdDt() != null
                && bean.getBundlemixEdDt().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getBundlemixEdDt()) + "'");
        }
        if (bean.getBundlemixSttimeQt() != null
                && bean.getBundlemixSttimeQt().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getBundlemixSttimeQt())
                    + "'");
        }
        if (bean.getBundlemixEdtimeQt() != null
                && bean.getBundlemixEdtimeQt().trim().length() != 0) {
            if (aftKanma)
                sb.append(",");
            else
                aftKanma = true;
            sb.append("'" + conv.convertString(bean.getBundlemixEdtimeQt())
                    + "'");
        }
        if (aftKanma)
            sb.append(",");
        sb.append(bean.getSeirituKosuQtString());
        sb.append(",");
        sb.append(bean.getSeirituKingakuVlString());
        sb.append(",");
        sb.append(bean.getTankaVlString());
        if (bean.getJisiMonFg() != null
                && bean.getJisiMonFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiMonFg()) + "'");
        }
        if (bean.getJisiTueFg() != null
                && bean.getJisiTueFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiTueFg()) + "'");
        }
        if (bean.getJisiWedFg() != null
                && bean.getJisiWedFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiWedFg()) + "'");
        }
        if (bean.getJisiThuFg() != null
                && bean.getJisiThuFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiThuFg()) + "'");
        }
        if (bean.getJisiFriFg() != null
                && bean.getJisiFriFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiFriFg()) + "'");
        }
        if (bean.getJisiSatFg() != null
                && bean.getJisiSatFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiSatFg()) + "'");
        }
        if (bean.getJisiSunFg() != null
                && bean.getJisiSunFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getJisiSunFg()) + "'");
        }
        if (bean.getInsertTs() != null
                && bean.getInsertTs().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getInsertTs()) + "'");
        }
        if (bean.getInsertUserId() != null
                && bean.getInsertUserId().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getInsertUserId()) + "'");
        }
        if (bean.getUpdateTs() != null
                && bean.getUpdateTs().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
        }
        if (bean.getUpdateUserId() != null
                && bean.getUpdateUserId().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getUpdateUserId()) + "'");
        }
        if (bean.getDeleteFg() != null
                && bean.getDeleteFg().trim().length() != 0) {
            sb.append(",");
            sb.append("'" + conv.convertString(bean.getDeleteFg()) + "'");
        }

		//A.Tozaki
		if (bean.getSinkiTorokuDt() != null
				&& bean.getSinkiTorokuDt().trim().length() != 0) {
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getSinkiTorokuDt()) + "'");
		}
		if (bean.getHenkoDt() != null
				&& bean.getHenkoDt().trim().length() != 0) {
			sb.append(",");
			sb.append("'" + conv.convertString(bean.getHenkoDt()) + "'");
		}
		

        sb.append(")");
        return sb.toString();
    }

    /**
     * 更新用ＳＱＬの生成を行う。 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
     * 
     * @param beanMst
     *            Object
     * @return String 生成されたＳＱＬ
     */
    public String getUpdateSql(Object beanMst) {
        boolean befKanma = false;
        boolean whereAnd = false;
//        DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
        mst740101_BMCodeBean bean = (mst740101_BMCodeBean) beanMst;
        StringBuffer sb = new StringBuffer();
        sb.append("update ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//        sb.append("R_BANDLEMIX_CODE set ");
		sb.append("R_BUNDLEMIX_CD set ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
        if (bean.getNameNa() != null && bean.getNameNa().trim().length() != 0) {
            befKanma = true;
            sb.append(" name_na = ");
            sb.append("'" + conv.convertString(bean.getNameNa()) + "'");
        }
//        if (bean.getNameKa() != null && bean.getNameKa().trim().length() != 0) {
        if (bean.getDeleteFg() == null
                || bean.getDeleteFg().trim().length() == 0 || bean.getDeleteFg().equals(mst000801_DelFlagDictionary.INAI.getCode())) {
        	if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" name_ka = ");
            sb.append("'" + conv.convertString(bean.getNameKa()) + "'");
        }
        if (bean.getBundlemixStDt() != null
                && bean.getBundlemixStDt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_st_dt = ");
            sb.append("'" + conv.convertString(bean.getBundlemixStDt()) + "'");
        }
        if (bean.getBundlemixEdDt() != null
                && bean.getBundlemixEdDt().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_ed_dt = ");
            sb.append("'" + conv.convertString(bean.getBundlemixEdDt()) + "'");
        }
        //if (bean.getBandlemixSttimeQt() != null
        //        && bean.getBandlemixSttimeQt().trim().length() != 0) {
        if (bean.getDeleteFg() == null
                || bean.getDeleteFg().trim().length() == 0 || bean.getDeleteFg().equals(mst000801_DelFlagDictionary.INAI.getCode())) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_sttime_qt = ");
            sb.append("'" + conv.convertString(bean.getBundlemixSttimeQt())
                    + "'");
        }
        //if (bean.getBandlemixEdtimeQt() != null
        //        && bean.getBandlemixEdtimeQt().trim().length() != 0) {
        if (bean.getDeleteFg() == null
                || bean.getDeleteFg().trim().length() == 0 || bean.getDeleteFg().equals(mst000801_DelFlagDictionary.INAI.getCode())) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" bundlemix_edtime_qt = ");
            sb.append("'" + conv.convertString(bean.getBundlemixEdtimeQt())
                    + "'");
        }
        if (befKanma)
            sb.append(",");
        sb.append(" seiritu_kosu_qt = ");
        sb.append(bean.getSeirituKosuQtString());
        sb.append(",");
        sb.append(" seiritu_kingaku_vl = ");
        sb.append(bean.getSeirituKingakuVlString());
        sb.append(",");
        sb.append(" tanka_vl = ");
        sb.append(bean.getTankaVlString());
        if (bean.getJisiMonFg() != null
                && bean.getJisiMonFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_mon_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiMonFg()) + "'");
        }
        if (bean.getJisiTueFg() != null
                && bean.getJisiTueFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_tue_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiTueFg()) + "'");
        }
        if (bean.getJisiWedFg() != null
                && bean.getJisiWedFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_wed_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiWedFg()) + "'");
        }
        if (bean.getJisiThuFg() != null
                && bean.getJisiThuFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_thu_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiThuFg()) + "'");
        }
        if (bean.getJisiFriFg() != null
                && bean.getJisiFriFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_fri_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiFriFg()) + "'");
        }
        if (bean.getJisiSatFg() != null
                && bean.getJisiSatFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_sat_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiSatFg()) + "'");
        }
        if (bean.getJisiSunFg() != null
                && bean.getJisiSunFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" jisi_sun_fg = ");
            sb.append("'" + conv.convertString(bean.getJisiSunFg()) + "'");
        }
        if (bean.getInsertTs() != null
                && bean.getInsertTs().trim().length() != 0) {
            sb.append(",");
            sb.append(" insert_ts = ");
            sb.append("'" + conv.convertString(bean.getInsertTs()) + "'");
        }
        if (bean.getInsertUserId() != null
                && bean.getInsertUserId().trim().length() != 0) {
            sb.append(",");
            sb.append(" insert_user_id = ");
            sb.append("'" + conv.convertString(bean.getInsertUserId()) + "'");
        }
        if (bean.getUpdateTs() != null
                && bean.getUpdateTs().trim().length() != 0) {
            sb.append(",");
            sb.append(" update_ts = ");
            sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
        }
        if (bean.getUpdateUserId() != null
                && bean.getUpdateUserId().trim().length() != 0) {
            sb.append(",");
            sb.append(" update_user_id = ");
            sb.append("'" + conv.convertString(bean.getUpdateUserId()) + "'");
        }
        if (bean.getDeleteFg() != null
                && bean.getDeleteFg().trim().length() != 0) {
            sb.append(",");
            sb.append(" delete_fg = ");
            sb.append("'" + conv.convertString(bean.getDeleteFg()) + "'");
        }

		//A.Tozaki
		if (bean.getSinkiTorokuDt() != null
				&& bean.getSinkiTorokuDt().trim().length() != 0) {
			sb.append(",");
			sb.append(" sinki_toroku_dt = ");
			sb.append("'" + conv.convertString(bean.getSinkiTorokuDt()) + "'");
		}
		if (bean.getHenkoDt() != null
				&& bean.getHenkoDt().trim().length() != 0) {
			sb.append(",");
			sb.append(" henko_dt = ");
			sb.append("'" + conv.convertString(bean.getHenkoDt()) + "'");
		}

		

        sb.append(" WHERE");

        if (bean.getBundlemixCd() != null
                && bean.getBundlemixCd().trim().length() != 0) {
            whereAnd = true;
            sb.append(" bundlemix_cd = ");
            sb.append("'" + conv.convertWhereString(bean.getBundlemixCd())
                    + "'");
        }
        return sb.toString();
    }

    /**
     * 更新用ＳＱＬ（論理削除用）の生成を行う。 渡されたBEANを元にＤＢを更新するためのＳＱＬ。
     * 
     * @param beanMst
     *            Object
     * @return String 生成されたＳＱＬ
     */
    public String getUpdateSqlForLogicalDelete(Object beanMst) {
        boolean befKanma = false;
        boolean whereAnd = false;
//        DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
        mst740101_BMCodeBean bean = (mst740101_BMCodeBean) beanMst;
        StringBuffer sb = new StringBuffer();
        sb.append("update ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//        sb.append("R_BANDLEMIX_CODE set ");
		sb.append("R_BUNDLEMIX_CD set ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑

        if (bean.getUpdateTs() != null
                && bean.getUpdateTs().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" update_ts = ");
            sb.append("'" + conv.convertString(bean.getUpdateTs()) + "'");
        }
        if (bean.getUpdateUserId() != null
                && bean.getUpdateUserId().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" update_user_id = ");
            sb.append("'" + conv.convertString(bean.getUpdateUserId()) + "'");
        }
        if (bean.getDeleteFg() != null
                && bean.getDeleteFg().trim().length() != 0) {
            if (befKanma)
                sb.append(",");
            else
                befKanma = true;
            sb.append(" delete_fg = ");
            sb.append("'" + conv.convertString(bean.getDeleteFg()) + "'");
        }
		
		//A.Tozaki
		if (bean.getHenkoDt() != null
				&& bean.getHenkoDt().trim().length() != 0) {
			if (befKanma)
				sb.append(",");
			else
				befKanma = true;
			sb.append(" henko_dt = ");
			sb.append("'" + conv.convertString(bean.getHenkoDt()) + "'");
		}



        sb.append(" WHERE");

        if (bean.getBundlemixCd() != null
                && bean.getBundlemixCd().trim().length() != 0) {
            whereAnd = true;
            sb.append(" bundlemix_cd = ");
            sb.append("'" + conv.convertWhereString(bean.getBundlemixCd())
                    + "'");
        }
        if (whereAnd)
            sb.append(" AND ");
        else
            whereAnd = true;
        sb.append(" delete_fg = '" + mst000801_DelFlagDictionary.INAI.getCode()
                + "'");
        return sb.toString();
    }

    /**
     * 削除用ＳＱＬの生成を行う。 渡されたBEANをＤＢから削除するためのＳＱＬ。
     * 
     * @param beanMst
     *            Object
     * @return String 生成されたＳＱＬ
     */
    public String getDeleteSql(Object beanMst) {
//        DBStringConvert conv = DBStringConvert.getDBStringConvert(getDatabaseProductName());
        mst740101_BMCodeBean bean = (mst740101_BMCodeBean) beanMst;
        StringBuffer sb = new StringBuffer();
        sb.append("delete from ");
		//↓↓　呼び出す子画面の変更（2005.06.21）　↓↓
//        sb.append("R_BANDLEMIX_CODE where ");
		sb.append("R_BUNDLEMIX_CD where ");
		//↑↑　呼び出す子画面の変更（2005.06.21）　↑↑
        sb.append(" bundlemix_cd = ");
        sb.append("'" + conv.convertWhereString(bean.getBundlemixCd()) + "'");
        return sb.toString();
    }

    /**
     * JDK1.4からは使用できるようになったString.replaceAllをJDK1.3以前用に作成する。
     * 
     * @param base
     * @param before
     * @param after
     * @return
     */
    protected String replaceAll(String base, String before, String after) {
        if (base == null)
            return base;
        int pos = base.lastIndexOf(before);
        if (pos < 0)
            return base;
        int befLen = before.length();
        StringBuffer sb = new StringBuffer(base);
        while (pos >= 0 && (pos = base.lastIndexOf(before, pos)) >= 0) {
            sb.delete(pos, pos + befLen);
            sb.insert(pos, after);
            pos--;
        }
        return sb.toString();
    }
}