package mdware.master.batch.process.mb16;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.common.batch.log.BatchUserLog;
import mdware.common.batch.util.control.jobProperties.Jobs;
import mdware.common.resorces.util.ResorceUtil;
import mdware.common.util.DateChanger;
import mdware.master.common.dictionary.mst000101_ConstDictionary;
import mdware.master.common.dictionary.mst000801_DelFlagDictionary;
import mdware.master.util.db.MasterDataBase;

import org.apache.log4j.Level;

/**
 * <p>タイトル:不要過去世代レコード物理削除処理</p>
 * <p>説明:有効日をキーに持つテーブルは削除フラグに関係なく過去３世代以降は物理削除する。</p>
 * <p>     また、論理削除確定となった世代よりも過去の世代は物理削除する。</p>
 * <p>     また、論理削除されたデータについて指定期間を経過したレコードは物理削除する。</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @author VINX
 * @version 1.00 (2014/08/07) NGHIA-HT 海外LAWSON様通貨対応
 * @version 1.01 (2017/05/23) T.Han #4613 FIVIMART対応
 * @version 1.02 (2023/01/09) SIEU.D #6719 MKH対応
 */

public class MB160101_KakoSedaiButuriSakujyo {
	
	//DataBaseクラス
	private MasterDataBase db			= null;
	private boolean       closeDb		= false;
	
	// ログ
	private BatchLog		batchLog	= BatchLog.getInstance();
	private BatchUserLog	userLog		= BatchUserLog.getInstance();

	// バッチ日付
	private String 		batchDt 	= null;

	// 削除データ保持期間
	private int	 		purgeInterval = 0;

	// 対象テーブル識別
	private static final String TABLE_SYOHIN			= "R_SYOHIN";
	private static final String TABLE_KEIRYOKI		= "R_KEIRYOKI";
	private static final String TABLE_GIFT			= "R_GIFT_SYOHIN";
	private static final String TABLE_HACHU			= "R_SYOHIN_HACHUNOHINKIJUNBI";
    // 2017.05.23 T.Han #4613対応（S)
	private static final String TABLE_SYOHIN_ASN	= "R_SYOHIN_ASN";
    // 2017.05.23 T.Han #4613対応（E)

	// 削除対象世代
	private static final int PARAM_SEDAI_NUM			= 3;	// 不要過去世代とみなす世代数

	// 削除を行わない期間
	// 過去世代数が削除対象世代であっても削除を行わない猶予期間
	private static final int PARAM_SEDAI_KIKAN		= 0 - (Integer.parseInt(ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.FUYOKAKO_DATA_TAISYOGAI_SPAN)));

	
	
	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute( String batchId ) throws Exception {
		execute();
	}
		
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB160101_KakoSedaiButuriSakujyo(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB160101_KakoSedaiButuriSakujyo() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}
	
	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {
		// ①計量器マスタ、②ギフト商品マスタ、③商品マスタは個別処理とするため登録しない
		String tables[][] = {
			{ "R_TENSYOHIN_REIGAI",  "店別商品例外マスタ"   },
			{ "R_BUTURYUKEIRO",      "物流経路マスタ"       },
			{ "R_HACHUNOHINKIJUNBI", "発注納品基準日マスタ" },
			{ "R_TENPO_BUNRUI1",     "店分類１マスタ"       },
			{ "R_SYOHINKAISO",       "商品分類階層マスタ"   },
			{ "R_TAX_RATE",          "税率マスタ"           }
		    // 2017.05.23 T.Han #4613対応（S)
		   ,{ "R_SIIRESAKI_TEN_TORIATUKAI", "仕入先店別取扱マスタ" }
		    // 2017.05.23 T.Han #4613対応（E)
		};

		try{
			
			this.writeLog(Level.INFO_INT, "処理を開始します。");

			// システムコントロール情報取得
			this.getSystemControl();

			
			this.writeLog(Level.INFO_INT, "不要過去世代削除処理を開始します");

			// 商品マスタ、ギフト商品マスタ、計量器マスタ、商品マスタASN
			this.deleteSyohinMst();

			// 店別商品例外
			this.deleteTenSyohinRgMst();

			// 店分類１マスタ
			this.deleteTenpoBnrui1Mst();

			// 商品分類階層マスタ
			this.deleteSyohinKaisoMst();

			// 物流経路マスタ
			this.deleteButuryuMst();
			
			// 発注納品基準日マスタ
			this.deleteHachuNohinMst();

			// 税率マスタ
			this.deleteTaxRateMst();
			
		    // 2017.05.23 T.Han #4613対応（S)
			// 仕入先店別取扱マスタ
			this.deleteSiiresakiTenToriatukaiMst();
		    // 2017.05.23 T.Han #4613対応（E)

			this.writeLog(Level.INFO_INT, "不要過去世代削除処理を終了します");
	

			this.writeLog(Level.INFO_INT, "論理削除レコードの物理削除処理を開始します");
			
			// 商品マスタ、ギフト商品マスタ、計量器マスタ、商品発注納品基準日マスタ、商品マスタASN
			this.deleteSyohinDead();
			
			for (int i = 0; i < tables.length; i++) {
				this.deleteData( tables[i][0], tables[i][1] );
			}

			this.writeLog(Level.INFO_INT, "論理削除レコードの物理削除処理を終了します");

		// SQL例外処理
		}catch( SQLException se ){
			db.rollback();

			this.writeError(se);

			throw se;

		}catch( Exception e ){
			db.rollback();

			this.writeError(e);

			throw e;

		}finally{
			// クローズ
			if (closeDb || db != null) {
				db.close();
			}
		}
	}

	/**
	 * システムコントロール情報取得
	 * @param  なし
	 * @throws Exception 例外
	 */
	private void getSystemControl() throws Exception {

		String wkData	= null;

		// 初期化
		batchDt 	  = null;
		purgeInterval = 0;
		
    	// バッチ日付
		batchDt = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.BATCH_DT);

    	if(batchDt == null || batchDt.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、バッチ日付が取得できませんでした");
    		throw new Exception();
		}

    	// 論理削除データ保持期間
    	wkData = ResorceUtil.getInstance().getPropertie(mst000101_ConstDictionary.RONRI_SAKUJO_SPAN);

    	if(wkData == null || wkData.trim().length() == 0){
    		this.writeLog(Level.INFO_INT, "システムコントロールから、論理削除データ保持期間が取得できませんでした");
    		throw new Exception();
		} else {
			purgeInterval= Integer.parseInt( wkData );
		}
	}

	/**
	 * 商品マスタ関連削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteSyohinMst() throws SQLException, Exception {
		long delCnt;
		
		// ギフト商品マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnSql( TABLE_GIFT ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、ギフト商品マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShn3Sql( TABLE_GIFT ) );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタ上で有効日を迎えた削除データよりも過去のデータに該当するギフト商品マスタのデータを物理削除しました。");

		// 計量器マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnSql( TABLE_KEIRYOKI ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、計量器マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShn3Sql( TABLE_KEIRYOKI ) );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタ上で有効日を迎えた削除データよりも過去のデータに該当する計量器マスタのデータを物理削除しました。");

		// 商品発注納品基準日マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnSql( TABLE_HACHU ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、商品発注納品基準日マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShn3Sql( TABLE_HACHU ) );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタ上で有効日を迎えた削除データよりも過去のデータに該当する商品発注納品基準日マスタのデータを物理削除しました。");

	    // 2017.05.23 T.Han #4613対応（S)
		// 商品マスタASN
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnSql( TABLE_SYOHIN_ASN ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタASNの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShn3Sql( TABLE_SYOHIN_ASN ) );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタASN上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");
	    // 2017.05.23 T.Han #4613対応（E)

		// 商品マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnSql( TABLE_SYOHIN ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShn3Sql( TABLE_SYOHIN ) );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");
	}
	
	/**
	 * 商品マスタの不要過去世代レコード削除用
	 * @param  String
	 * @return String
	 */
	private String setDeleteShnSql( String tableName )  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE ");
		sb.append("   FROM " + tableName + " ");
		sb.append(" WHERE ");
		sb.append("     BUNRUI1_CD || SYOHIN_CD || YUKO_DT ");
		sb.append("     IN ( ");
		sb.append("         SELECT BUNRUI1_CD || SYOHIN_CD || YUKO_DT ");		
		sb.append("           FROM ( ");		
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT count(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                       SELECT * ");
		sb.append("                                         FROM R_SYOHIN ");
		sb.append("                                           WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                     ) h  ");
		// #6620 MOD 2022.11.18 VU.TD (S)
		//sb.append("                                  WHERE h.BUNRUI1_CD = A.BUNRUI1_CD ");
		//sb.append("                                    AND h.SYOHIN_CD = A.SYOHIN_CD ");
		sb.append("                                  WHERE  ");
		sb.append("                                    		h.SYOHIN_CD = A.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");
		sb.append("                             ) ROWNO ");
		sb.append("                   FROM R_SYOHIN A ");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");

		return sb.toString();
	}

	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShn3Sql( String tableName )
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("   FROM " + tableName + " ");
		sb.append(" WHERE BUNRUI1_CD || SYOHIN_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.BUNRUI1_CD || B.SYOHIN_CD || B.YUKO_DT ");
		sb.append("            FROM (SELECT BUNRUI1_CD, SYOHIN_CD, YUKO_DT ");
		sb.append("                    FROM R_SYOHIN ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_SYOHIN B");
		// #6620 MOD 2022.11.18 VU.TD (S)
		//sb.append("              ON B.BUNRUI1_CD  = A.BUNRUI1_CD ");
		//sb.append("             AND B.SYOHIN_CD = A.SYOHIN_CD ");
		sb.append("             ON B.SYOHIN_CD = A.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (S)
		sb.append("             AND B.YUKO_DT < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();
	}	
	
	/**
	 * 店別商品例外マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteTenSyohinRgMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnRgSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、店別商品例外マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShnRg3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、店別商品例外マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 店別商品例外マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShnRgSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_TENSYOHIN_REIGAI ");
		sb.append(" WHERE ");
		sb.append("     BUNRUI1_CD || SYOHIN_CD || TENPO_CD || YUKO_DT ");
		sb.append("     IN ( ");
		sb.append("         SELECT BUNRUI1_CD || SYOHIN_CD || TENPO_CD || YUKO_DT ");
		sb.append("           FROM ( ");
		// #6719 MOD 2022.01.09 SIEU.D (S)
//		sb.append("                 SELECT A.*, ( ");
//		sb.append("                              SELECT COUNT(*) ");
//		sb.append("                                FROM ( ");
//		sb.append("                                      SELECT *");
//		sb.append("                                        FROM R_TENSYOHIN_REIGAI   ");
//		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
//		sb.append("                                      ) h ");
//		// #6620 MOD 2022.11.18 VU.TD (S)
//		//sb.append("                                  WHERE h.BUNRUI1_CD = A.BUNRUI1_CD ");
//		sb.append("                                    AND h.SYOHIN_CD = A.SYOHIN_CD ");
//		sb.append("                                  WHERE h.SYOHIN_CD = A.SYOHIN_CD ");
//		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append("                                    AND h.TENPO_CD = A.TENPO_CD ");
//		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");		
//		sb.append("                              ) ROWNO ");		
//		sb.append("                   FROM R_TENSYOHIN_REIGAI A");
//		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 SELECT A.BUNRUI1_CD ");
		sb.append("                              , A.SYOHIN_CD ");
		sb.append("                              , A.TENPO_CD ");
		sb.append("                              , A.YUKO_DT ");
		sb.append("                              , COUNT(H.YUKO_DT) ROWNO ");
		sb.append("                 FROM R_TENSYOHIN_REIGAI A ");
		sb.append("                 LEFT JOIN R_TENSYOHIN_REIGAI H ");
		sb.append("                 ON H.SYOHIN_CD   = A.SYOHIN_CD ");
		sb.append("                   AND H.TENPO_CD   = A.TENPO_CD ");
		sb.append("                   AND H.YUKO_DT   >= A.YUKO_DT ");
		sb.append("                   AND H.YUKO_DT   <=  '"+ batchDt +"' ");
		sb.append("                 WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 GROUP BY A.BUNRUI1_CD ");
		sb.append("                          , A.SYOHIN_CD  ");
		sb.append("                          , A.TENPO_CD  ");
		sb.append("                          , A.YUKO_DT  ");
		// #6719 MOD 2022.01.09 SIEU.D (E)
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShnRg3Sql()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_TENSYOHIN_REIGAI ");
		sb.append(" WHERE BUNRUI1_CD || SYOHIN_CD || TENPO_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.BUNRUI1_CD || B.SYOHIN_CD || B.TENPO_CD || B.YUKO_DT ");
		sb.append("            FROM (SELECT BUNRUI1_CD, SYOHIN_CD, TENPO_CD, YUKO_DT ");
		sb.append("                    FROM R_TENSYOHIN_REIGAI ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_TENSYOHIN_REIGAI B");
		// #6620 MOD 2022.11.18 VU.TD (S)
//		sb.append("              ON B.BUNRUI1_CD = A.BUNRUI1_CD ");
//		sb.append("             AND B.SYOHIN_CD = A.SYOHIN_CD ");
		sb.append("              ON  ");
		sb.append("             	B.SYOHIN_CD = A.SYOHIN_CD ");
		// #6620 MOD 2022.11.18 VU.TD (E)
		sb.append("             AND B.TENPO_CD = A.TENPO_CD ");
		sb.append("             AND B.YUKO_DT < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();
	}
	
	/**
	 * 店分類１マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteTenpoBnrui1Mst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteTnBSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、店分類１マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteTnB3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、店分類１マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 店分類１マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteTnBSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_TENPO_BUNRUI1 ");
		sb.append(" WHERE ");
		sb.append("     TENPO_CD || BUNRUI1_CD || YUKO_DT ");
		sb.append("     IN ( ");
		sb.append("         SELECT TENPO_CD || BUNRUI1_CD || YUKO_DT ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT * ");
		sb.append("                                        FROM R_TENPO_BUNRUI1   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.TENPO_CD = A.TENPO_CD ");
		sb.append("                                    AND h.BUNRUI1_CD = A.BUNRUI1_CD ");
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");		
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_TENPO_BUNRUI1 A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteTnB3Sql()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_TENPO_BUNRUI1 ");
		sb.append(" WHERE  TENPO_CD || BUNRUI1_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.TENPO_CD || B.BUNRUI1_CD || B.YUKO_DT ");
		sb.append("            FROM (SELECT TENPO_CD, BUNRUI1_CD, YUKO_DT ");
		sb.append("                    FROM R_TENPO_BUNRUI1 ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_TENPO_BUNRUI1 B");
		sb.append("              ON B.BUNRUI1_CD = A.BUNRUI1_CD ");
		sb.append("             AND B.TENPO_CD = A.TENPO_CD ");
		sb.append("             AND B.YUKO_DT < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();
	}
	
    // 2017.05.23 T.Han #4613対応（S)
	/**
	 * 仕入先店別取扱マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteSiiresakiTenToriatukaiMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteSTTSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、仕入先店別取扱マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteSTT3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、仕入先店別取扱マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 仕入先店別取扱マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteSTTSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_SIIRESAKI_TEN_TORIATUKAI ");
		sb.append(" WHERE ");
		sb.append("     SIIRESAKI_CD || TENPO_CD || YUKO_DT ");
		sb.append("     IN ( ");
		sb.append("         SELECT SIIRESAKI_CD || TENPO_CD || YUKO_DT ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT * ");
		sb.append("                                        FROM R_SIIRESAKI_TEN_TORIATUKAI   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.SIIRESAKI_CD = A.SIIRESAKI_CD ");
		sb.append("                                    AND h.TENPO_CD = A.TENPO_CD ");
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");		
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_SIIRESAKI_TEN_TORIATUKAI A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteSTT3Sql()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_SIIRESAKI_TEN_TORIATUKAI ");
		sb.append(" WHERE  SIIRESAKI_CD || TENPO_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.SIIRESAKI_CD || B.TENPO_CD || B.YUKO_DT ");
		sb.append("            FROM (SELECT SIIRESAKI_CD, TENPO_CD, YUKO_DT ");
		sb.append("                    FROM R_SIIRESAKI_TEN_TORIATUKAI ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_SIIRESAKI_TEN_TORIATUKAI B");
		sb.append("              ON B.SIIRESAKI_CD = A.SIIRESAKI_CD ");
		sb.append("             AND B.TENPO_CD = A.TENPO_CD ");
		sb.append("             AND B.YUKO_DT < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();
	}
	// 2017.05.23 T.Han #4613対応（E)

	/**
	 * 商品階層マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteSyohinKaisoMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnKsSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、商品階層マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteShnKs3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、商品階層マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 商品階層マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShnKsSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_SYOHINKAISO ");
		sb.append(" WHERE ");
		sb.append("     KAISOU_PATTERN_KB || CODE1_CD || YUKO_DT || SYSTEM_KB ");
		sb.append("     IN ( ");
		sb.append("         SELECT KAISOU_PATTERN_KB || CODE1_CD || YUKO_DT || SYSTEM_KB ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT * ");
		sb.append("                                        FROM R_SYOHINKAISO   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.KAISOU_PATTERN_KB = A.KAISOU_PATTERN_KB ");
		sb.append("                                    AND h.CODE1_CD = A.CODE1_CD ");
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");		
		sb.append("                                    AND h.SYSTEM_KB = A.SYSTEM_KB ");
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_SYOHINKAISO A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShnKs3Sql()
	{
		StringBuffer sb=new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_SYOHINKAISO ");
		sb.append(" WHERE KAISOU_PATTERN_KB || SYSTEM_KB || CODE1_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.KAISOU_PATTERN_KB || B.SYSTEM_KB || B.CODE1_CD || B.YUKO_DT ");
		sb.append("            FROM (SELECT KAISOU_PATTERN_KB, SYSTEM_KB, CODE1_CD, YUKO_DT ");
		sb.append("                    FROM R_SYOHINKAISO ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT   <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_SYOHINKAISO B");
		sb.append("              ON B.KAISOU_PATTERN_KB = A.KAISOU_PATTERN_KB ");
		sb.append("             AND B.SYSTEM_KB         = A.SYSTEM_KB ");
		sb.append("             AND B.CODE1_CD          = A.CODE1_CD ");
		sb.append("             AND B.YUKO_DT           < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();		
	}

	/**
	 * 物流経路マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteButuryuMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteBtKrSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、物流経路マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteBtKr3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、物流経路マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 物流経路マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteBtKrSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_BUTURYUKEIRO ");
		sb.append(" WHERE ");
		sb.append("     KANRI_KB || KANRI_CD || SIHAI_KB || SIHAI_CD || SYOHIN_CD || TENPO_CD || YUKO_DT || BUTURYU_KB ");
		sb.append("     IN ( ");
		sb.append("         SELECT KANRI_KB || KANRI_CD || SIHAI_KB || SIHAI_CD || SYOHIN_CD || TENPO_CD || YUKO_DT || BUTURYU_KB ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT *");
		sb.append("                                        FROM R_BUTURYUKEIRO   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.KANRI_KB = A.KANRI_KB ");
		sb.append("                                    AND h.KANRI_CD = A.KANRI_CD ");
		sb.append("                                    AND h.SIHAI_KB = A.SIHAI_KB ");
		sb.append("                                    AND h.SIHAI_CD = A.SIHAI_CD ");
		sb.append("                                    AND h.SYOHIN_CD = A.SYOHIN_CD ");
		sb.append("                                    AND h.TENPO_CD = A.TENPO_CD ");
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");
		sb.append("                                    AND h.BUTURYU_KB = A.BUTURYU_KB ");
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_BUTURYUKEIRO A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteBtKr3Sql()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM R_BUTURYUKEIRO RB ");
		sb.append("WHERE EXISTS(");
		sb.append("SELECT 'X' ");
		sb.append("FROM (SELECT B.KANRI_KB ,");
		sb.append("  B.KANRI_CD ,");
		sb.append("  B.SIHAI_KB ,");
		sb.append("  B.SIHAI_CD ,");
		sb.append("  B.SYOHIN_CD ,");
		sb.append("  B.TENPO_CD ,");
		sb.append("  B.YUKO_DT ,");
		sb.append("  B.BUTURYU_KB");
		sb.append(" FROM (SELECT KANRI_KB,");
		sb.append("   KANRI_CD,");
		sb.append("   SIHAI_KB,");
		sb.append("   SIHAI_CD,");
		sb.append("   SYOHIN_CD,");
		sb.append("   TENPO_CD,");
		sb.append("   YUKO_DT,");
		sb.append("   BUTURYU_KB");
		sb.append("  FROM R_BUTURYUKEIRO");
		sb.append("  WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "'");
		sb.append("  AND YUKO_DT <= '" + batchDt + "') A INNER JOIN R_BUTURYUKEIRO B");
		sb.append(" ON B.KANRI_KB = A.KANRI_KB");
		sb.append(" AND B.KANRI_CD = A.KANRI_CD");
		sb.append(" AND B.SIHAI_KB = A.SIHAI_KB");
		sb.append(" AND B.SIHAI_CD = A.SIHAI_CD");
		sb.append(" AND B.SYOHIN_CD = A.SYOHIN_CD");
		sb.append(" AND B.TENPO_CD = A.TENPO_CD");
		sb.append(" AND B.BUTURYU_KB = A.BUTURYU_KB");
		sb.append(" AND B.YUKO_DT < A.YUKO_DT) DTMP");
		sb.append(" WHERE  RB.KANRI_KB = DTMP.KANRI_KB ");
		sb.append(" AND RB.KANRI_CD = DTMP.KANRI_CD");
		sb.append(" AND RB.SIHAI_KB = DTMP.SIHAI_KB ");
		sb.append(" AND RB.SIHAI_CD = DTMP.SIHAI_CD ");
		sb.append(" AND RB.SYOHIN_CD = DTMP.SYOHIN_CD ");
		sb.append(" AND RB.TENPO_CD = DTMP.TENPO_CD ");
		sb.append(" AND RB.YUKO_DT = DTMP.YUKO_DT ");
		sb.append(" AND RB.BUTURYU_KB = DTMP.BUTURYU_KB)");

		return sb.toString();
	}
	
	/**
	 * 発注納品基準日マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteHachuNohinMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteHNKSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、発注納品基準日マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteHNK3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、発注納品基準日マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 発注納品基準日マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteHNKSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_HACHUNOHINKIJUNBI ");
		sb.append(" WHERE ");
		sb.append("     YUKO_DT || KANRI_KB || KANRI_CD || SIIRESAKI_CD || HACHU_HOHO_KB ");
		sb.append("     IN ( ");
		sb.append("         SELECT YUKO_DT || KANRI_KB || KANRI_CD || SIIRESAKI_CD || HACHU_HOHO_KB ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT * ");
		sb.append("                                        FROM R_HACHUNOHINKIJUNBI   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.YUKO_DT >= A.YUKO_DT ");
		sb.append("                                    AND h.KANRI_KB = A.KANRI_KB ");		
		sb.append("                                    AND h.KANRI_CD = A.KANRI_CD ");
		sb.append("                                    AND h.SIIRESAKI_CD = A.SIIRESAKI_CD ");		
		sb.append("                                    AND h.HACHU_HOHO_KB = A.HACHU_HOHO_KB ");
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_HACHUNOHINKIJUNBI A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteHNK3Sql()
	{
		StringBuffer sb=new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_HACHUNOHINKIJUNBI ");
		sb.append(" WHERE YUKO_DT || KANRI_KB || KANRI_CD || SIIRESAKI_CD || HACHU_HOHO_KB ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.YUKO_DT || B.KANRI_KB || B.KANRI_CD || B.SIIRESAKI_CD || B.HACHU_HOHO_KB ");
		sb.append("            FROM (SELECT YUKO_DT, KANRI_KB, KANRI_CD, SIIRESAKI_CD, HACHU_HOHO_KB ");
		sb.append("                    FROM R_HACHUNOHINKIJUNBI ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT   <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_HACHUNOHINKIJUNBI B");
		sb.append("              ON B.KANRI_KB      = A.KANRI_KB ");		
		sb.append("             AND B.KANRI_CD      = A.KANRI_CD ");
		sb.append("             AND B.SIIRESAKI_CD  = A.SIIRESAKI_CD ");		
		sb.append("             AND B.HACHU_HOHO_KB = A.HACHU_HOHO_KB ");
		sb.append("             AND B.YUKO_DT       < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();		
	}

	/**
	 * 税率マスタ削除処理
	 * @param  なし
	 * @return なし
	 */	
	private void deleteTaxRateMst() throws SQLException, Exception {
		long delCnt;
		
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteTaxRtSql() );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件、税率マスタの不要過去世代レコードを物理削除しました。");

		delCnt = 0;
		delCnt=db.executeUpdate( this.setDeleteTaxRt3Sql() );
		db.commit();

		this.writeLog(Level.INFO_INT, delCnt + "件、税率マスタ上で有効日を迎えた削除データよりも過去のデータを物理削除しました。");

	}
	
	/**
	 * 税率マスタの不要過去世代レコード削除用
	 * @param  なし
	 * @return String
	 */
	private String setDeleteTaxRtSql()  {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM ");
		sb.append("     R_TAX_RATE ");
		sb.append(" WHERE ");
		sb.append("     TAX_RATE_KB || YUKO_DT ");
		sb.append("     IN ( ");
		sb.append("         SELECT TAX_RATE_KB || YUKO_DT ");
		sb.append("           FROM ( ");
		sb.append("                 SELECT A.*, ( ");
		sb.append("                              SELECT COUNT(*) ");
		sb.append("                                FROM ( ");
		sb.append("                                      SELECT * ");
		sb.append("                                        FROM R_TAX_RATE   ");
		sb.append("                                          WHERE YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                                      ) h ");
		sb.append("                                  WHERE h.TAX_RATE_KB = A.TAX_RATE_KB ");
		sb.append("                                    AND h.YUKO_DT >= A.YUKO_DT ");		
		sb.append("                              ) ROWNO ");		
		sb.append("                   FROM R_TAX_RATE A");
		sb.append("                     WHERE A.YUKO_DT <= '"+ batchDt +"' ");
		sb.append("                 ) TBL ");
		sb.append("             WHERE ROWNO > "+ PARAM_SEDAI_NUM +" ");
		// 過去３世代目以降でも、有効日一定期間内であれば、削除対象としない
		sb.append("             AND   YUKO_DT <= '"+ (DateChanger.addDate(batchDt,PARAM_SEDAI_KIKAN)) +"' ");
		sb.append("         )");
		
		return sb.toString();
	}		
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteTaxRt3Sql()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("  FROM R_TAX_RATE ");
		sb.append(" WHERE TAX_RATE_KB || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.TAX_RATE_KB || B.YUKO_DT ");
		sb.append("            FROM (SELECT TAX_RATE_KB, YUKO_DT ");
		sb.append("                    FROM R_TAX_RATE ");
		sb.append("                   WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("                     AND YUKO_DT <= '" + batchDt + "' ");
		sb.append("                 ) A ");
		sb.append("           INNER JOIN ");
		sb.append("                 R_TAX_RATE B");
		sb.append("              ON B.TAX_RATE_KB = A.TAX_RATE_KB ");
		sb.append("             AND B.YUKO_DT < A.YUKO_DT ");
		sb.append("        ) ");

		return sb.toString();
	}
	
	
	/**
	 * 論理削除確定データの不要過去レコード物理削除処理
	 * @param String
	 * @param int
	 * @return
	 */	
	private void deleteSyohinDead() throws SQLException, Exception {
		int delCnt = 0;

		// 計量器マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnDeadSql( TABLE_KEIRYOKI ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。(計量器マスタ)");

		// ギフト商品マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnDeadSql( TABLE_GIFT ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。(ギフト商品マスタ)");
	
		// 商品発注納品基準日マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnDeadSql( TABLE_HACHU ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。(商品発注納品基準日マスタ)");
	
	    // 2017.05.23 T.Han #4613対応（S)
		// 商品マスタASN
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnDeadSql( TABLE_SYOHIN_ASN) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。(商品マスタASN)");
	    // 2017.05.23 T.Han #4613対応（E)

		// 商品マスタ
		delCnt = 0;
		delCnt = db.executeUpdate( this.setDeleteShnDeadSql( TABLE_SYOHIN ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。(商品マスタ)");
	

	}
	
	/**
	 * 有効日を迎えた削除データの過去のデータを全て物理削除するSQL
	 * @param  なし
	 * @return String
	 */
	private String setDeleteShnDeadSql( String tableName )
	{
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE ");
		sb.append("   FROM " + tableName + " ");
		sb.append(" WHERE BUNRUI1_CD || SYOHIN_CD || YUKO_DT ");
		sb.append("    IN ( ");
		sb.append("          SELECT B.BUNRUI1_CD || B.SYOHIN_CD || B.YUKO_DT ");
		sb.append("            FROM R_SYOHIN B ");
		sb.append("          WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("            AND B.YUKO_DT <= '" + DateChanger.addDate(batchDt, 0 - purgeInterval) + "' ");
		sb.append("        ) ");

		return sb.toString();
	}	
	
	/**
	 * 論理削除確定データの不要過去レコード物理削除処理
	 * @param String
	 * @param int
	 * @return
	 */	
	private void deleteData( String buturiTblName, String ronriTblName ) throws SQLException, Exception {
		int delCnt = 0;

		delCnt = db.executeUpdate( getDeleteSql( buturiTblName ) );
		db.commit();
		
		this.writeLog(Level.INFO_INT, delCnt + "件の不要過去レコードを物理削除しました。("+ ronriTblName +")");
	
	}
	
	/**
	 * 削除データを物理削除するSQL
	 * @param String tblname
	 * @return
	 */	
	private String getDeleteSql( String tblname ) {
		
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM " + tblname + " ");
		sb.append("  WHERE DELETE_FG = '" ).append( mst000801_DelFlagDictionary.IRU.getCode() ).append( "' " );
		sb.append("    AND YUKO_DT <= '" + DateChanger.addDate(batchDt, 0 - purgeInterval) + "' ");

		return sb.toString();
	  }	

	/**
	 * ユーザーログとバッチログにログを出力します。
	 * 
	 * @param level 出力レベル。 Levelクラスの定数を指定。
	 * @param message 出力させたいメッセージ。 ユーザーログ、バッチログに同じ文字列が出力されます。
	 */
	private void writeLog(int level, String message){
		String jobId = userLog.getJobId();

		switch(level){
		case Level.DEBUG_INT:
			userLog.debug(message);
			batchLog.getLog().debug(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.INFO_INT:
			userLog.info(message);
			batchLog.getLog().info(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;

		case Level.ERROR_INT:
			userLog.error(message);
			batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
			
		case Level.FATAL_INT:
			userLog.fatal(message);
			batchLog.getLog().fatal(jobId ,Jobs.getInstance().get(jobId).getJobName(), message);
			break;
		}
	}
	
	/**
	 * エラーをログファイルに出力します。
	 * ユーザーログへは固定文言のみ出力、バッチログへはエラー内容を出力します。
	 * 
	 * @param e 発生したException
	 */
	private void writeError(Exception e) {
		if (e instanceof SQLException) {
			userLog.error("ＳＱＬエラーが発生しました。");
		} else {
			userLog.error("エラーが発生しました。");
		}

		String jobId = userLog.getJobId();
		batchLog.getLog().error(jobId ,Jobs.getInstance().get(jobId).getJobName(), "エラーが発生しました。");
		batchLog.getLog().error(e.toString());

		StackTraceElement[] elements = e.getStackTrace();
		for (int tmp = 0; tmp < elements.length; tmp++) {
			batchLog.getLog().error(elements[tmp].getClassName() + " : line " + elements[tmp].getLineNumber());
		}
	}
}
