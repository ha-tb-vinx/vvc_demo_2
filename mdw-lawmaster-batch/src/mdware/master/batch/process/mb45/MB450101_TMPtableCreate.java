package mdware.master.batch.process.mb45;

import java.sql.SQLException;

import mdware.common.batch.log.BatchLog;
import mdware.master.util.db.MasterDataBase;
import mdware.common.util.db.MDWareDBUtility;
import java.sql.ResultSet;

import mdware.common.batch.util.BatchStatusManager;
/**
 * <p>タイトル:各マスタ(全件コピー)生成</p>
 * <p>著作権: Copyright (c) 2004</p>
 * <p>会社名: Vinculum Japan Corp.</p>
 * @version 1.00 2005/08/05<BR>
 * @author sawabe
 */
 
public class MB450101_TMPtableCreate {

	private MasterDataBase db	= null;
	private boolean closeDb 		= false;
	//batchID
	private String batchID;

	//ログ出力用変数
	private BatchLog batchLog = BatchLog.getInstance();
	private static final String BATCH_ID = "MB45-01-01";
	private static final String BATCH_NA = "TMP_R_テーブル作成処理";
	
	/**
	 * コンストラクタ
	 * @param dataBase
	 */
	public MB450101_TMPtableCreate(MasterDataBase db) {
		this.db = db;
		if (db == null) {
			this.db = new MasterDataBase("rbsite_ora");
			closeDb = true;
		}
	}

	/**
	 * コンストラクタ
	 */
	public MB450101_TMPtableCreate() {
		this(new MasterDataBase("rbsite_ora"));
		closeDb = true;
	}


	/**
	 * 外部からの実行メソッド
	 * @param batchId バッチJobId
	 * @throws Exception 例外
	 */
	public void execute(String batchId) throws Exception {
		batchID = batchId;
		execute();
	}

	/**
	 * 本処理
	 * @throws Exception
	 */
	public void execute() throws Exception {

//      ↓↓2006.07.24 fanglh カスタマイズ修正↓↓
//		//SQL文生成用
//		StringBuffer strSql = new StringBuffer();
//		//バッチ登録件数をカウント
//		int iRec = 0;
//		
//		//テスト用
////		String IDX = "TABLESPACE IDX_EDI_TEST";
//
//		
//		try{
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓↓↓ TMP_R_各マスタ(全件コピー)を開始します。↓↓↓");
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_ALARMを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_ALARM");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_ALARM AS (SELECT * FROM R_ALARM) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_ALARM");
////			strSql.append("  ADD CONSTRAINT PK_TMP_R_ALARM PRIMARY KEY (TORIKOMI_DT,EXCEL_FILE_SYUBETSU,UKETSUKE_NO,SHEET_SYUBETSU)");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_ALARM PRIMARY KEY (TORIKOMI_DT,EXCEL_FILE_SYUBETSU,S_GYOSYU_CD,UKETSUKE_NO,SHEET_SYUBETSU)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_ALARMを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_ALARM_KAKUNINを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_ALARM_KAKUNIN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_ALARM_KAKUNIN AS (SELECT * FROM R_ALARM_KAKUNIN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_ALARM_KAKUNIN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_ALARM_KAKUNIN PRIMARY KEY (TORIKOMI_DT,EXCEL_FILE_SYUBETSU,UKETSUKE_NO,SHEET_SYUBETSU,USER_ID)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_ALARM_KAKUNINを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_ASSORTMENTを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_ASSORTMENT");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_ASSORTMENT AS (SELECT * FROM R_ASSORTMENT) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_ASSORTMENT");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_ASSORTMENT PRIMARY KEY (SYOHIN_CD,SIZE_CD,COLOR_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_ASSORTMENTを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BM_KIKAKUTOKUBAI_RENKANを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BM_KIKAKUTOKUBAI_RENKAN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BM_KIKAKUTOKUBAI_RENKAN AS (SELECT * FROM R_BM_KIKAKUTOKUBAI_RENKAN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BM_KIKAKUTOKUBAI_RENKAN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BM_KIKAKUTOKUBAI_RNKN PRIMARY KEY (KIKAKU_TOKUBAINO_CD,BUNDLEMIX_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BM_KIKAKUTOKUBAI_RENKANを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BUNDLEMIX_CDを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BUNDLEMIX_CD");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BUNDLEMIX_CD AS (SELECT * FROM R_BUNDLEMIX_CD) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BUNDLEMIX_CD");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BUNDLEMIX_CD PRIMARY KEY (BUNDLEMIX_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BUNDLEMIX_CDを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BUNDLEMIX_KIKAKUTOKUBAIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BUNDLEMIX_KIKAKUTOKUBAI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BUNDLEMIX_KIKAKUTOKUBAI AS (SELECT * FROM R_BUNDLEMIX_KIKAKUTOKUBAI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BUNDLEMIX_KIKAKUTOKUBAI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BUNDLEMIX_KIKAKUTOKUB PRIMARY KEY (KIKAKU_TOKUBAINO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BUNDLEMIX_KIKAKUTOKUBAIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BUNDLEMIX_SYOHINを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BUNDLEMIX_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BUNDLEMIX_SYOHIN AS (SELECT * FROM R_BUNDLEMIX_SYOHIN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BUNDLEMIX_SYOHIN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BUNDLEMIX_SYOHIN PRIMARY KEY (BUNDLEMIX_CD,HANKU_CD,SYOHIN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BUNDLEMIX_SYOHINを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BUTURYUKEIROを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BUTURYUKEIRO");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BUTURYUKEIRO AS (SELECT * FROM R_BUTURYUKEIRO) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BUTURYUKEIRO");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BUTURYUKEIRO PRIMARY KEY (KANRI_KB,KANRI_CD,SIHAI_KB,SIHAI_CD,SYOHIN_CD,TENPO_CD,YUKO_DT,BUTURYU_KB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BUTURYUKEIROを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_BUTURYUTENPOを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_BUTURYUTENPO");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_BUTURYUTENPO AS (SELECT * FROM R_BUTURYUTENPO) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_BUTURYUTENPO");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_BUTURYUTENPO PRIMARY KEY (TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_BUTURYUTENPOを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HACHUNOHINKIJUNBIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_HACHUNOHINKIJUNBI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_HACHUNOHINKIJUNBI AS (SELECT * FROM R_HACHUNOHINKIJUNBI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_HACHUNOHINKIJUNBI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_HACHUNOHINKIJUNBI PRIMARY KEY (YUKO_DT,KANRI_KB,KANRI_CD,SIIRESAKI_CD,HACHU_HOHO_KB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_HACHUNOHINKIJUNBIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HACHUNOHIN_NGを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_HACHUNOHIN_NG");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_HACHUNOHIN_NG AS (SELECT * FROM R_HACHUNOHIN_NG) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_HACHUNOHIN_NG");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_HACHUNOHIN_NG PRIMARY KEY (TENPO_CD,YMD_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_HACHUNOHIN_NGを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HAISOUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_HAISOU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_HAISOU AS (SELECT * FROM R_HAISOU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_HAISOU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_HAISOU PRIMARY KEY (KANRI_CD,HAISOSAKI_CD,KANRI_KB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_HAISOUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HAKOKANRIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_HAKOKANRI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_HAKOKANRI AS (SELECT * FROM R_HAKOKANRI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_HAKOKANRI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_HAKOKANRI PRIMARY KEY (SHIHAI_KB,SHIHAI_CD,TENPO_CD,YUKO_DT,KANRI_KB,KANRI_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_HAKOKANRIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HINSYU_SENDを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_HINSYU_SEND");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_HINSYU_SEND AS (SELECT * FROM R_HINSYU_SEND) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_HINSYU_SEND");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_HINSYU_SEND PRIMARY KEY (KANRI_CD,KANRI_KB,TENPO_CD,SEND_DT,SELECT_KB,ENTRY_KB,HINSYU_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_HINSYU_SENDを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_JIDOHACHU_CTLを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_JIDOHACHU_CTL");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_JIDOHACHU_CTL AS (SELECT * FROM R_JIDOHACHU_CTL) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_JIDOHACHU_CTL");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_JIDOHACHU_CTL PRIMARY KEY (KANRI_KB,KANRI_CD,TENPO_CD,YUKO_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_JIDOHACHU_CTLを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_JIDOHAIBANを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_JIDOHAIBAN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_JIDOHAIBAN AS (SELECT * FROM R_JIDOHAIBAN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_JIDOHAIBAN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_JIDOHAIBAN PRIMARY KEY (KANRI_KB,KANRI_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_JIDOHAIBANを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_KAKAKUCHECKを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_KAKAKUCHECK");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_KAKAKUCHECK AS (SELECT * FROM R_KAKAKUCHECK) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_KAKAKUCHECK");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_KAKAKUCHECK PRIMARY KEY (HANKU_CD,SYOHIN_CD,KAKAKU_KB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_KAKAKUCHECKを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_KEIRYOKIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_KEIRYOKI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_KEIRYOKI AS (SELECT * FROM R_KEIRYOKI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_KEIRYOKI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_KEIRYOKI PRIMARY KEY (KEIRYO_HANKU_CD,SYOHIN_YOBIDASI,S_GYOSYU_CD,THEME_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_KEIRYOKIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_KEIRYOKI_THEMEを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_KEIRYOKI_THEME");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_KEIRYOKI_THEME AS (SELECT * FROM R_KEIRYOKI_THEME) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_KEIRYOKI_THEME");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_KEIRYOKI_THEME PRIMARY KEY (S_GYOSYU_CD,THEME_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_KEIRYOKI_THEMEを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_KEYPLUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_KEYPLU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_KEYPLU AS (SELECT * FROM R_KEYPLU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_KEYPLU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_KEYPLU PRIMARY KEY (KEYPLU_NO_NB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_KEYPLUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_LAST_ENTRY_NO_KANRIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_LAST_ENTRY_NO_KANRI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_LAST_ENTRY_NO_KANRI AS (SELECT * FROM R_LAST_ENTRY_NO_KANRI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_LAST_ENTRY_NO_KANRI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_LAST_ENTRY_NO_KANRI PRIMARY KEY (ENTRY_SYUBETU_CD,ENTRY_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_LAST_ENTRY_NO_KANRIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_MESSAGEを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_MESSAGE");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_MESSAGE AS (SELECT * FROM R_MESSAGE) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_MESSAGEを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_MST_DATEを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_MST_DATE");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
//			strSql.append("CREATE TABLE TMP_R_MST_DATE AS (SELECT MST_DATE_DT,BAT_DATE_DT,MST_USE_FG,INSERT_TS,INSERT_USER_ID,UPDATE_TS,UPDATE_USER_ID FROM SYSTEM_CONTROL) WITH DATA ");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_MST_DATEを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_MST_SEQを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_MST_SEQ");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_MST_SEQ AS (SELECT * FROM R_MST_SEQ) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_MST_SEQ");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_MST_SEQ PRIMARY KEY (SAIBAN_ID)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_MST_SEQを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_MST_SYSTEM_CONFを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_MST_SYSTEM_CONF");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_MST_SYSTEM_CONF AS (SELECT * FROM R_MST_SYSTEM_CONF) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_MST_SYSTEM_CONFを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_NAMECTFを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_NAMECTF");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_NAMECTF AS (SELECT * FROM R_NAMECTF) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_NAMECTF");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_NAMECTF PRIMARY KEY (SYUBETU_NO_CD,CODE_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_NAMECTFを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_8KETAを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SAIBAN_8KETA");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SAIBAN_8KETA AS (SELECT * FROM R_SAIBAN_8KETA) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SAIBAN_8KETA");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SAIBAN_8KETA PRIMARY KEY (HINSYU_CD,TANPIN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SAIBAN_8KETAを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_COLORSIZEを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SAIBAN_COLORSIZE");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SAIBAN_COLORSIZE AS (SELECT * FROM R_SAIBAN_COLORSIZE) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SAIBAN_COLORSIZE");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SAIBAN_COLORSIZE PRIMARY KEY (HINSYU_CD,COLOR_CD,SIZE_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SAIBAN_COLORSIZEを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_INSTOREを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SAIBAN_INSTORE");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SAIBAN_INSTORE AS (SELECT * FROM R_SAIBAN_INSTORE) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SAIBAN_INSTORE");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SAIBAN_INSTORE PRIMARY KEY (TANPIN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SAIBAN_INSTOREを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_WAKU_HINSYUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SAIBAN_WAKU_HINSYU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SAIBAN_WAKU_HINSYU AS (SELECT * FROM R_SAIBAN_WAKU_HINSYU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SAIBAN_WAKU_HINSYU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SAIBAN_WAKU_HINSYU PRIMARY KEY (HINSYU_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SAIBAN_WAKU_HINSYUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SETSYOHINを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SETSYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SETSYOHIN AS (SELECT * FROM R_SETSYOHIN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SETSYOHIN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SETSYOHIN PRIMARY KEY (HANKU_CD,SETSYOHIN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SETSYOHINを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SIIRESAKIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SIIRESAKI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SIIRESAKI AS (SELECT * FROM R_SIIRESAKI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SIIRESAKI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SIIRESAKI PRIMARY KEY (KANRI_KB,KANRI_CD,SIIRESAKI_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SIIRESAKIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SIIRE_HACHUNOHIN_NGを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SIIRE_HACHUNOHIN_NG");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SIIRE_HACHUNOHIN_NG AS (SELECT * FROM R_SIIRE_HACHUNOHIN_NG) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SIIRE_HACHUNOHIN_NG");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SIIRE_HACHUNOHIN_NG PRIMARY KEY (KANRI_KB,KANRI_CD,SIIRESAKI_CD,DATE_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SIIRE_HACHUNOHIN_NGを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHINを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOHIN AS (SELECT * FROM R_SYOHIN) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOHIN");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOHIN PRIMARY KEY (HANKU_CD,SYOHIN_CD,YUKO_DT)");
//			iRec = db.executeUpdate(strSql.toString());
//			
//			//INDEX 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("CREATE INDEX IDX1_TMP_R_SYOHIN ON TMP_R_SYOHIN");
//			strSql.append("  (SYOHIN_CD,YUKO_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			//INDEX 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("CREATE INDEX IDX2_TMP_R_SYOHIN ON TMP_R_SYOHIN");
//			strSql.append("  (HINSYU_CD)");
////			↓↓移植（2006.05.12）↓↓
//			iRec = db.executeUpdate(strSql.toString());
//			
//			//INDEX 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("CREATE INDEX IDX3_TMP_R_SYOHIN ON TMP_R_SYOHIN");
//			strSql.append("  (SYOHIN_2_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOHINを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHINKAISOを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOHINKAISO");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOHINKAISO AS (SELECT * FROM R_SYOHINKAISO) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOHINKAISO");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOHINKAISO PRIMARY KEY (YUKO_DT,KAISOU_PATTERN_KB,CODE1_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOHINKAISOを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHIN_CONVERTを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOHIN_CONVERT");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOHIN_CONVERT AS (SELECT * FROM R_SYOHIN_CONVERT) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOHIN_CONVERT");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOHIN_CONVERT PRIMARY KEY (HANKU_CD,SYOHIN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOHIN_CONVERTを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHIN_IKKATU_MENTEを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOHIN_IKKATU_MENTE");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOHIN_IKKATU_MENTE AS (SELECT * FROM R_SYOHIN_IKKATU_MENTE) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOHIN_IKKATU_MENTE");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOHIN_IKKATU_MENTE PRIMARY KEY (TOUROKU_TS,HANKU_CD,SYOHIN_CD,TOROKU_USER_ID)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOHIN_IKKATU_MENTEを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHIN_TAIKEIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOHIN_TAIKEI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOHIN_TAIKEI AS (SELECT * FROM R_SYOHIN_TAIKEI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOHIN_TAIKEI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOHIN_TAIKEI PRIMARY KEY (HINSYU_CD)");
//			iRec = db.executeUpdate(strSql.toString());
//			
//			//INDEX 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("CREATE INDEX IDX1_TMP_R_SYOHIN_TAIKEI ON TMP_R_SYOHIN_TAIKEI");
//			strSql.append("  (D_GYOSYU_CD,HANKU_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOHIN_TAIKEIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOKAIDONYUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_SYOKAIDONYU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_SYOKAIDONYU AS (SELECT * FROM R_SYOKAIDONYU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_SYOKAIDONYU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_SYOKAIDONYU PRIMARY KEY (HACHUNO_CD,TENPO_CD,HANKU_CD,SYOHIN_CD,COLOR_CD,SIZE_CD,ASORT_PATTERN_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_SYOKAIDONYUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANPINTEN_JIDOSAKUSEIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TANPINTEN_JIDOSAKUSEI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TANPINTEN_JIDOSAKUSEI AS (SELECT * FROM R_TANPINTEN_JIDOSAKUSEI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TANPINTEN_JIDOSAKUSEI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TANPINTEN_JIDOSAKUSEI PRIMARY KEY (KANRI_KB,KANRI_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TANPINTEN_JIDOSAKUSEIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANPINTEN_TORIATUKAIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TANPINTEN_TORIATUKAI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TANPINTEN_TORIATUKAI AS (SELECT * FROM R_TANPINTEN_TORIATUKAI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TANPINTEN_TORIATUKAI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TANPINTEN_TORIATUKAI PRIMARY KEY (HANKU_CD,SYOHIN_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TANPINTEN_TORIATUKAIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANPIN_SENDを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TANPIN_SEND");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TANPIN_SEND AS (SELECT * FROM R_TANPIN_SEND) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TANPIN_SEND");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TANPIN_SEND PRIMARY KEY (KANRI_KB,KANRI_CD,TENPO_CD,SEND_DT,REQUEST_KB,ENTRY_KB,SYOHIN_CD,HANKU_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TANPIN_SENDを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENBETU_HAISOSAKIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENBETU_HAISOSAKI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENBETU_HAISOSAKI AS (SELECT * FROM R_TENBETU_HAISOSAKI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENBETU_HAISOSAKI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENBETU_HAISOSAKI PRIMARY KEY (KANRI_KB,KANRI_CD,DAIHYO_HAISO_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENBETU_HAISOSAKIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENBETU_KEYPLUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENBETU_KEYPLU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENBETU_KEYPLU AS (SELECT * FROM R_TENBETU_KEYPLU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENBETU_KEYPLU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENBETU_KEYPLU PRIMARY KEY (TENPO_CD,KEYPLU_NO_NB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENBETU_KEYPLUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENBETU_SIIRESAKIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENBETU_SIIRESAKI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENBETU_SIIRESAKI AS (SELECT * FROM R_TENBETU_SIIRESAKI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENBETU_SIIRESAKI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENBETU_SIIRESAKI PRIMARY KEY (TEN_SIIRESAKI_KANRI_CD,TENPO_CD,KANRI_KB,KANRI_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENBETU_SIIRESAKIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENGROUPを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENGROUP");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENGROUP AS (SELECT * FROM R_TENGROUP) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENGROUP");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENGROUP PRIMARY KEY (L_GYOSYU_CD,YOTO_KB,GROUPNO_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENGROUPを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENGROUPNOを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENGROUPNO");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENGROUPNO AS (SELECT * FROM R_TENGROUPNO) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENGROUPNO");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENGROUPNO PRIMARY KEY (L_GYOSYU_CD,GROUPNO_CD,YOTO_KB)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENGROUPNOを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENHANKUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENHANKU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENHANKU AS (SELECT * FROM R_TENHANKU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENHANKU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENHANKU PRIMARY KEY (KANRI_KB,KANRI_CD,TENPO_CD,YUKO_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENHANKUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENKABUTUを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENKABUTU");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENKABUTU AS (SELECT * FROM R_TENKABUTU) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENKABUTU");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENKABUTU PRIMARY KEY (TENKABUTU_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENKABUTUを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENKIKAKUTOKUBAIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENKIKAKUTOKUBAI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENKIKAKUTOKUBAI AS (SELECT * FROM R_TENKIKAKUTOKUBAI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENKIKAKUTOKUBAI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENKIKAKUTOKUBAI PRIMARY KEY (TENPO_CD,KIKAKU_TOKUBAINO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENKIKAKUTOKUBAIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENPOを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENPO");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENPO AS (SELECT * FROM R_TENPO) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENPO");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENPO PRIMARY KEY (TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENPOを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENSYOHIN_REIGAIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_TENSYOHIN_REIGAI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_TENSYOHIN_REIGAI AS (SELECT * FROM R_TENSYOHIN_REIGAI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_TENSYOHIN_REIGAI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_TENSYOHIN_REIGAI PRIMARY KEY (HANKU_CD,SYOHIN_CD,TENPO_CD,YUKO_DT)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_TENSYOHIN_REIGAIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_THEME_TENHANEIBIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_THEME_TENHANEIBI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_THEME_TENHANEIBI AS (SELECT * FROM R_THEME_TENHANEIBI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_THEME_TENHANEIBI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_THEME_TENHANEIBI PRIMARY KEY (S_GYOSYU_CD,THEME_CD,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_THEME_TENHANEIBIを終了します。↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_YOBIDASINO_TENHANEIBIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE TMP_R_YOBIDASINO_TENHANEIBI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE TMP_R_YOBIDASINO_TENHANEIBI AS (SELECT * FROM R_YOBIDASINO_TENHANEIBI) WITH DATA");
//			iRec = db.executeUpdate(strSql.toString());
//			db.commit();
//			
//			//PRIMARY KEY 追加
//			strSql.delete(0,strSql.length());
//			strSql.append("ALTER TABLE TMP_R_YOBIDASINO_TENHANEIBI");
//			strSql.append("  ADD CONSTRAINT PK_TMP_R_YOBIDASINO_TENHANEIBI PRIMARY KEY (S_GYOSYU_CD,THEME_CD,KEIRYO_HANKU_CD,SYOHIN_YOBIDASI,TENPO_CD)");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_R_YOBIDASINO_TENHANEIBIを終了します。↑");
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑↑↑ TMP_R_各マスタ(全件コピー)を終了します。↑↑↑");
//			
//			/**************************************************************************************************
//			 *                   BK_BAT_各マスタ(BAT_xxx のバックアップデータ)作成処理
//			 **************************************************************************************************/
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓↓↓ BK_BAT_各マスタ(バックアップ)を開始します。↓↓↓");
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_ASSORTMENTを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_ASSORTMENT");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_ASSORTMENT AS (SELECT * FROM BAT_ASSORTMENT) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_ASSORTMENTを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BM_KIKAKUTOKUBAI_RENKANを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BM_KIKAKUTOKUBAI_RENKAN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BM_KIKAKUTOKUBAI_RENKAN AS (SELECT * FROM BAT_BM_KIKAKUTOKUBAI_RENKAN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BM_KIKAKUTOKUBAI_RENKANを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUNDLEMIX_CDを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUNDLEMIX_CD");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUNDLEMIX_CD AS (SELECT * FROM BAT_BUNDLEMIX_CD) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUNDLEMIX_CDを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUNDLEMIX_KIKAKUTOKUBAIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUNDLEMIX_KIKAKUTOKUBAI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUNDLEMIX_KIKAKUTOKUBAI AS (SELECT * FROM BAT_BUNDLEMIX_KIKAKUTOKUBAI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUNDLEMIX_KIKAKUTOKUBAIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUNDLEMIX_SYOHINを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUNDLEMIX_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUNDLEMIX_SYOHIN AS (SELECT * FROM BAT_BUNDLEMIX_SYOHIN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUNDLEMIX_SYOHINを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUTURYUKEIRO_HANKUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUTURYUKEIRO_HANKU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUTURYUKEIRO_HANKU AS (SELECT * FROM BAT_BUTURYUKEIRO_HANKU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUTURYUKEIRO_HANKUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUTURYUKEIRO_HANKU_JANを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUTURYUKEIRO_HANKU_JAN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUTURYUKEIRO_HANKU_JAN AS (SELECT * FROM BAT_BUTURYUKEIRO_HANKU_JAN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUTURYUKEIRO_HANKU_JANを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_BUTURYUTENPOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_BUTURYUTENPO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_BUTURYUTENPO AS (SELECT * FROM BAT_BUTURYUTENPO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_BUTURYUTENPOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HACHUNOHINKIJUN_HANKUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HACHUNOHINKIJUN_HANKU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HACHUNOHINKIJUN_HANKU AS (SELECT * FROM BAT_HACHUNOHINKIJUNBI_HANKU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HACHUNOHINKIJUN_HANKUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HACHUNOHINKIJUN_HINSYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HACHUNOHINKIJUN_HINSYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HACHUNOHINKIJUN_HINSYU AS (SELECT * FROM BAT_HACHUNOHINKIJUNBI_HINSYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HACHUNOHINKIJUN_HINSYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HACHUNOHIN_NGを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HACHUNOHIN_NG");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HACHUNOHIN_NG AS (SELECT * FROM BAT_HACHUNOHIN_NG) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HACHUNOHIN_NGを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAISOUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAISOU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAISOU AS (SELECT * FROM BAT_HAISOU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAISOUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAISOU_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAISOU_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAISOU_FRS AS (SELECT * FROM BAT_HAISOU_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAISOU_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAKOKANRI_HAISOSAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAKOKANRI_HAISOSAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAKOKANRI_HAISOSAKI AS (SELECT * FROM BAT_HAKOKANRI_HAISOSAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAKOKANRI_HAISOSAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAKOKANRI_HAISOSAKI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAKOKANRI_HAISOSAKI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAKOKANRI_HAISOSAKI_FRS AS (SELECT * FROM BAT_HAKOKANRI_HAISOSAKI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAKOKANRI_HAISOSAKI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAKOKANRI_SIIRESAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAKOKANRI_SIIRESAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAKOKANRI_SIIRESAKI AS (SELECT * FROM BAT_HAKOKANRI_SIIRESAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAKOKANRI_SIIRESAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HAKOKANRI_SIIRESAKI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HAKOKANRI_SIIRESAKI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HAKOKANRI_SIIRESAKI_FRS AS (SELECT * FROM BAT_HAKOKANRI_SIIRESAKI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HAKOKANRI_SIIRESAKI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_HINSYU_SENDを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_HINSYU_SEND");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_HINSYU_SEND AS (SELECT * FROM BAT_HINSYU_SEND) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_HINSYU_SENDを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_JIDOHACHU_CTL_HANKUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_JIDOHACHU_CTL_HANKU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_JIDOHACHU_CTL_HANKU AS (SELECT * FROM BAT_JIDOHACHU_CTL_HANKU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_JIDOHACHU_CTL_HANKUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_JIDOHACHU_CTL_HANKU_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_JIDOHACHU_CTL_HANKU_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_JIDOHACHU_CTL_HANKU_FRS AS (SELECT * FROM BAT_JIDOHACHU_CTL_HANKU_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_JIDOHACHU_CTL_HANKU_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_JIDOHACHU_CTL_HINSYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_JIDOHACHU_CTL_HINSYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_JIDOHACHU_CTL_HINSYU AS (SELECT * FROM BAT_JIDOHACHU_CTL_HINSYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_JIDOHACHU_CTL_HINSYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_JIDOHACHU_CTL_HINSYU_FSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_JIDOHACHU_CTL_HINSYU_FS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_JIDOHACHU_CTL_HINSYU_FS AS (SELECT * FROM BAT_JIDOHACHU_CTL_HINSYU_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_JIDOHACHU_CTL_HINSYU_FSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_JIDOHAIBANを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_JIDOHAIBAN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_JIDOHAIBAN AS (SELECT * FROM BAT_JIDOHAIBAN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_JIDOHAIBANを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KAKAKUCHECKを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KAKAKUCHECK");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KAKAKUCHECK AS (SELECT * FROM BAT_KAKAKUCHECK) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KAKAKUCHECKを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KARITANPINTENを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KARITANPINTEN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KARITANPINTEN AS (SELECT * FROM BAT_KARITANPINTEN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KARITANPINTENを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KARITANPINTEN_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KARITANPINTEN_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KARITANPINTEN_FRS AS (SELECT * FROM BAT_KARITANPINTEN_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KARITANPINTEN_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KEIRYOKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KEIRYOKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KEIRYOKI AS (SELECT * FROM BAT_KEIRYOKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KEIRYOKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KEIRYOKI_THEMEを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KEIRYOKI_THEME");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KEIRYOKI_THEME AS (SELECT * FROM BAT_KEIRYOKI_THEME) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KEIRYOKI_THEMEを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_KEYPLUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_KEYPLU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_KEYPLU AS (SELECT * FROM BAT_KEYPLU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_KEYPLUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_LAST_ENTRY_NO_KANRIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_LAST_ENTRY_NO_KANRI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_LAST_ENTRY_NO_KANRI AS (SELECT * FROM BAT_LAST_ENTRY_NO_KANRI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_LAST_ENTRY_NO_KANRIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_MST_DATEを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_MST_DATE");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_MST_DATE AS (SELECT * FROM BAT_MST_DATE) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_MST_DATEを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_NAMECTFを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_NAMECTF");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_NAMECTF AS (SELECT * FROM BAT_NAMECTF) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_NAMECTFを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SAIBAN_8KETAを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SAIBAN_8KETA");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SAIBAN_8KETA AS (SELECT * FROM BAT_SAIBAN_8KETA) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SAIBAN_8KETAを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SAIBAN_COLORSIZEを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SAIBAN_COLORSIZE");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SAIBAN_COLORSIZE AS (SELECT * FROM BAT_SAIBAN_COLORSIZE) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SAIBAN_COLORSIZEを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SAIBAN_INSTOREを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SAIBAN_INSTORE");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SAIBAN_INSTORE AS (SELECT * FROM BAT_SAIBAN_INSTORE) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SAIBAN_INSTOREを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SAIBAN_WAKU_HINSYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SAIBAN_WAKU_HINSYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SAIBAN_WAKU_HINSYU AS (SELECT * FROM BAT_SAIBAN_WAKU_HINSYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SAIBAN_WAKU_HINSYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SETSYOHINを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SETSYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SETSYOHIN AS (SELECT * FROM BAT_SETSYOHIN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SETSYOHINを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SIIRESAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SIIRESAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SIIRESAKI AS (SELECT * FROM BAT_SIIRESAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SIIRESAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SIIRESAKI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SIIRESAKI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SIIRESAKI_FRS AS (SELECT * FROM BAT_SIIRESAKI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SIIRESAKI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SIIRE_HACHUNHN_NG_HANKUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SIIRE_HACHUNHN_NG_HANKU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SIIRE_HACHUNHN_NG_HANKU AS (SELECT * FROM BAT_SIIRE_HACHUNOHIN_NG_HANKU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SIIRE_HACHUNHN_NG_HANKUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SIIRE_HACHUNH_NG_HINSYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SIIRE_HACHUNH_NG_HINSYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SIIRE_HACHUNH_NG_HINSYU AS (SELECT * FROM BAT_SIIRE_HACHUNOHIN_NG_HINSYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SIIRE_HACHUNH_NG_HINSYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHINを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHIN AS (SELECT * FROM BAT_SYOHIN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHINを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHINKAISOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHINKAISO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHINKAISO AS (SELECT * FROM BAT_SYOHINKAISO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHINKAISOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHIN_CONVERTを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHIN_CONVERT");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHIN_CONVERT AS (SELECT * FROM BAT_SYOHIN_CONVERT) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHIN_CONVERTを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHIN_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHIN_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHIN_FRS AS (SELECT * FROM BAT_SYOHIN_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHIN_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHIN_IKKATU_MENTEを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHIN_IKKATU_MENTE");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHIN_IKKATU_MENTE AS (SELECT * FROM BAT_SYOHIN_IKKATU_MENTE) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHIN_IKKATU_MENTEを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOHIN_TAIKEIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOHIN_TAIKEI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOHIN_TAIKEI AS (SELECT * FROM BAT_SYOHIN_TAIKEI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOHIN_TAIKEIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYOKAIDONYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYOKAIDONYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYOKAIDONYU AS (SELECT * FROM BAT_SYOKAIDONYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYOKAIDONYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYS_GAMEN_NAMEを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYS_GAMEN_NAME");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYS_GAMEN_NAME AS (SELECT * FROM BAT_SYS_GAMEN_NAME) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYS_GAMEN_NAMEを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYS_KENGENを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYS_KENGEN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYS_KENGEN AS (SELECT * FROM BAT_SYS_KENGEN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYS_KENGENを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYS_KENGEN_RIYOUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYS_KENGEN_RIYOU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYS_KENGEN_RIYOU AS (SELECT * FROM BAT_SYS_KENGEN_RIYOU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYS_KENGEN_RIYOUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_SYS_SOSASYAを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_SYS_SOSASYA");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_SYS_SOSASYA AS (SELECT * FROM BAT_SYS_SOSASYA) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_SYS_SOSASYAを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TANPINTEN_JIDOSAKUSEIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TANPINTEN_JIDOSAKUSEI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TANPINTEN_JIDOSAKUSEI AS (SELECT * FROM BAT_TANPINTEN_JIDOSAKUSEI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TANPINTEN_JIDOSAKUSEIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TANPINTEN_TORIATUKAIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TANPINTEN_TORIATUKAI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TANPINTEN_TORIATUKAI AS (SELECT * FROM BAT_TANPINTEN_TORIATUKAI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TANPINTEN_TORIATUKAIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TANPIN_TORIATUKAI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TANPIN_TORIATUKAI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TANPIN_TORIATUKAI_FRS AS (SELECT * FROM BAT_TANPINTEN_TORIATUKAI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TANPINTEN_TORIATUKAI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TANPIN_SENDを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TANPIN_SEND");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TANPIN_SEND AS (SELECT * FROM BAT_TANPIN_SEND) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TANPIN_SENDを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENBETU_HAISOSAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENBETU_HAISOSAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENBETU_HAISOSAKI AS (SELECT * FROM BAT_TENBETU_HAISOSAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENBETU_HAISOSAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENBETU_HAISOSAKI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENBETU_HAISOSAKI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENBETU_HAISOSAKI_FRS AS (SELECT * FROM BAT_TENBETU_HAISOSAKI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENBETU_HAISOSAKI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENBETU_KEYPLUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENBETU_KEYPLU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENBETU_KEYPLU AS (SELECT * FROM BAT_TENBETU_KEYPLU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENBETU_KEYPLUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENBETU_SIIRESAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENBETU_SIIRESAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENBETU_SIIRESAKI AS (SELECT * FROM BAT_TENBETU_SIIRESAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENBETU_SIIRESAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENBETU_SIIRESAKI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENBETU_SIIRESAKI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENBETU_SIIRESAKI_FRS AS (SELECT * FROM BAT_TENBETU_SIIRESAKI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENBETU_SIIRESAKI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENGROUPを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENGROUP");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENGROUP AS (SELECT * FROM BAT_TENGROUP) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENGROUPを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENGROUPNOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENGROUPNO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENGROUPNO AS (SELECT * FROM BAT_TENGROUPNO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENGROUPNOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENHANKUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENHANKU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENHANKU AS (SELECT * FROM BAT_TENHANKU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENHANKUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENHANKU_HANKU_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENHANKU_HANKU_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENHANKU_HANKU_FRS AS (SELECT * FROM BAT_TENHANKU_HANKU_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENHANKU_HANKU_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENHANKU_HINSYU_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENHANKU_HINSYU_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENHANKU_HINSYU_FRS AS (SELECT * FROM BAT_TENHANKU_HINSYU_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENHANKU_HINSYU_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENKABUTUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENKABUTU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENKABUTU AS (SELECT * FROM BAT_TENKABUTU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENKABUTUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENKIKAKUTOKUBAIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENKIKAKUTOKUBAI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENKIKAKUTOKUBAI AS (SELECT * FROM BAT_TENKIKAKUTOKUBAI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENKIKAKUTOKUBAIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENPOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENPO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENPO AS (SELECT * FROM BAT_TENPO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENPOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENSYOHIN_REIGAIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENSYOHIN_REIGAI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENSYOHIN_REIGAI AS (SELECT * FROM BAT_TENSYOHIN_REIGAI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENSYOHIN_REIGAIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_TENSYOHIN_REIGAI_FRSを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_TENSYOHIN_REIGAI_FRS");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_TENSYOHIN_REIGAI_FRS AS (SELECT * FROM BAT_TENSYOHIN_REIGAI_FRS) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_TENSYOHIN_REIGAI_FRSを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_THEME_TENHANEIBIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_THEME_TENHANEIBI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_THEME_TENHANEIBI AS (SELECT * FROM BAT_THEME_TENHANEIBI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_THEME_TENHANEIBIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_BAT_YOBIDASINO_TENHANEIBIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_BAT_YOBIDASINO_TENHANEIBI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_BAT_YOBIDASINO_TENHANEIBI AS (SELECT * FROM BAT_YOBIDASINO_TENHANEIBI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_BAT_YOBIDASINO_TENHANEIBIを終了します。↑");
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑↑↑ BK_BAT_各マスタ(バックアップ)を終了します。↑↑↑");
//			
//			/******************************************************************************************************
//			 *                     BK_TMP_GOT(TMP_GOT_xxx のバックアップデータ)作成処理
//			 ******************************************************************************************************/			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓↓↓ BK_TMP_GOT_各マスタ(バックアップ)を開始します。↓↓↓");
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_CENTERを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_CENTER");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_CENTER AS (SELECT * FROM TMP_GOT_CENTER) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_CENTERを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_HACHU_CALENDARを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_HACHU_CALENDAR");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_HACHU_CALENDAR AS (SELECT * FROM TMP_GOT_HACHU_CALENDAR) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_HACHU_CALENDARを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_HAISINSAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_HAISINSAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_HAISINSAKI AS (SELECT * FROM TMP_GOT_HAISINSAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_HAISINSAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_HAISOSAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_HAISOSAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_HAISOSAKI AS (SELECT * FROM TMP_GOT_HAISOSAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_HAISOSAKIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_HANSOKU_SYOHINを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_HANSOKU_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_HANSOKU_SYOHIN AS (SELECT * FROM TMP_GOT_HANSOKU_SYOHIN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_HANSOKU_SYOHINを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_HIBRIDを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_HIBRID");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_HIBRID AS (SELECT * FROM TMP_GOT_HIBRID) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_HIBRIDを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_KAISOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_KAISO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_KAISO AS (SELECT * FROM TMP_GOT_KAISO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_KAISOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_SYOHINを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_SYOHIN");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_SYOHIN AS (SELECT * FROM TMP_GOT_SYOHIN) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_SYOHINを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_TANPIN_TORIATUKAIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_TANPIN_TORIATUKAI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_TANPIN_TORIATUKAI AS (SELECT * FROM TMP_GOT_TANPINTEN_TORIATUKAI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_TANPIN_TORIATUKAIを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_TENKYUを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_TENKYU");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_TENKYU AS (SELECT * FROM TMP_GOT_TENKYU) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_TENKYUを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_TENPOを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_TENPO");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_TENPO AS (SELECT * FROM TMP_GOT_TENPO) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_TMP_GOT_TENPOを終了します。↑");
//
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_TMP_GOT_TORIHIKISAKIを開始します。↓");
//
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_TMP_GOT_TORIHIKISAKI");
//				iRec = db.executeUpdate(strSql.toString());
//
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_TMP_GOT_TORIHIKISAKI AS (SELECT * FROM TMP_GOT_TORIHIKISAKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑TMP_GOT_TORIHIKISAKIを終了します。↑");
//
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑↑↑ BK_TMP_GOT_各マスタ(バックアップ)を終了します。↑↑↑");
//			
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓BK_WK_M_TANPIN_IF_RIREKIを開始します。↓");
//			
//			//テーブル削除
//			try{
//				strSql.delete(0,strSql.length());
//				strSql.append("DROP TABLE BK_WK_M_TANPIN_IF_RIREKI");
//				iRec = db.executeUpdate(strSql.toString());
//			
//			//SQLの例外処理
//			}catch(SQLException se){
//				db.rollback();
//				batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			}
//			
//			//テーブル作成
//			strSql.delete(0,strSql.length());
////			↓↓移植（2006.05.12）↓↓
//			strSql.append("CREATE TABLE BK_WK_M_TANPIN_IF_RIREKI AS (SELECT * FROM WK_M_TANPIN_IF_RIREKI) WITH DATA");
////			↑↑移植（2006.05.12）↑↑
//			iRec = db.executeUpdate(strSql.toString());
//			
//			batchLog.getLog().info(BATCH_ID, BATCH_NA, "↑BK_WK_M_TANPIN_IF_RIREKIを終了します。↑");
//
//
//		//SQLエラーが発生した場合の処理
//		}catch(SQLException se){
//			db.rollback();
//			batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
//			throw se;
//
//		//その他のエラーが発生した場合の処理
//		}catch(Exception e){
//			db.rollback();
//			batchLog.getLog().error(BATCH_ID, BATCH_NA, e.toString());
//			throw e;
//
//		//SQL終了処理
//		}finally{
//			db.close();
//		}
//	}

		// テーブル元
		String table_fm ;
		
		// テーブル先
		String table_to ;
		
		// バッチ登録件数をカウント
		int iRec = 0;

		batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓↓↓ TMP_R_各マスタ(全件コピー)のデータ処理を開始します。↓↓↓");
			//		20060822 shuuパッチステータス処理追加Start
			BatchStatusManager bsManager = new BatchStatusManager();
			//		20060822 shuuパッチステータス処理追加End
		try {

			//		20060822 shuuパッチステータス処理追加Start
			bsManager.statusRegisterProcStart(batchID);
			//		20060822 shuuパッチステータス処理追加End
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_HAKOKANRIのデータ処理を開始します。↓");
			table_fm = "R_HAKOKANRI";
			table_to = "TMP_R_HAKOKANRI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_HAKOKANRIのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_JIDOHAIBANのデータ処理を開始します。↓");
			table_fm = "R_JIDOHAIBAN";
			table_to = "TMP_R_JIDOHAIBAN";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_JIDOHAIBANのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_KEIYAKUSUのデータ処理を開始します。↓");
			table_fm = "R_KEIYAKUSU";
			table_to = "TMP_R_KEIYAKUSU";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_KEIYAKUSUのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_MESSAGEのデータ処理を開始します。↓");
			table_fm = "R_MESSAGE";
			table_to = "TMP_R_MESSAGE";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_MESSAGEのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_NAMECTFのデータ処理を開始します。↓");
			table_fm = "R_NAMECTF";
			table_to = "TMP_R_NAMECTF";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_NAMECTFのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_RIYO_USERのデータ処理を開始します。↓");
			table_fm = "R_RIYO_USER";
			table_to = "TMP_R_RIYO_USER";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_RIYO_USERのデータ処理を終了します。↑");

			
			
			
			
			
			
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_A08のデータ処理を開始します。↓");
			table_fm = "R_SAIBAN_A08";
			table_to = "TMP_R_SAIBAN_A08";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SAIBAN_A08のデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SAIBAN_A07のデータ処理を開始します。↓");
			table_fm = "R_SAIBAN_A07";
			table_to = "TMP_R_SAIBAN_A07";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SAIBAN_A07のデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SEQのデータ処理を開始します。↓");
			table_fm = "R_SEQ";
			table_to = "TMP_R_SEQ";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
					
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SEQのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SIIRESAKIのデータ処理を開始します。↓");
			table_fm = "R_SIIRESAKI";
			table_to = "TMP_R_SIIRESAKI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SIIRESAKIのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SUBCLASSのデータ処理を開始します。↓");
			table_fm = "R_SUBCLASS";
			table_to = "TMP_R_SUBCLASS";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SUBCLASSのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHINのデータ処理を開始します。↓");
			table_fm = "R_SYOHIN";
			table_to = "TMP_R_SYOHIN";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SYOHINのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHIN_IKKATU_MENTEのデータ処理を開始します。↓");
			table_fm = "R_SYOHIN_IKKATU_MENTE";
			table_to = "TMP_R_SYOHIN_IKKATU_MENTE";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SYOHIN_IKKATU_MENTEのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHIN_TAIKEIのデータ処理を開始します。↓");
			table_fm = "R_SYOHIN_TAIKEI";
			table_to = "TMP_R_SYOHIN_TAIKEI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SYOHIN_TAIKEIのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOHINKAISOのデータ処理を開始します。↓");
			table_fm = "R_SYOHINKAISO";
			table_to = "TMP_R_SYOHINKAISO";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SYOHINKAISOのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_SYOKAIDONYUのデータ処理を開始します。↓");
			table_fm = "R_SYOKAIDONYU";
			table_to = "TMP_R_SYOKAIDONYU";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_SYOKAIDONYUのデータ処理を終了します。↑");

//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANA_LOCATIONのデータ処理を開始します。↓");
//			table_fm = "R_TANA_LOCATION";
//			table_to = "TMP_R_TANA_LOCATION";
//			// コピー先テーブルのレコードを全件削除する
//			//2006.08.22 fang UPDATE START
//			//iRec = db.executeUpdate(SetDeleteSql(table_to));
//			clrpfm(table_to);
//			//2006.08.22 fang UPDATE END
//			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
//			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
//			
//			cpyf(table_fm,table_to);
//			db.commit();
//			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
//			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TANA_LOCATIONのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANADAIのデータ処理を開始します。↓");
			table_fm = "R_TANADAI";
			table_to = "TMP_R_TANADAI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TANADAIのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANPIN_SENDのデータ処理を開始します。↓");
			table_fm = "R_TANPIN_SEND";
			table_to = "TMP_R_TANPIN_SEND";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TANPIN_SENDのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TANPINTEN_JIDOSAKUSEIのデータ処理を開始します。↓");
			table_fm = "R_TANPINTEN_JIDOSAKUSEI";
			table_to = "TMP_R_TANPINTEN_JIDOSAKUSEI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TANPINTEN_JIDOSAKUSEIのデータ処理を終了します。↑");


			// 2008/10/14 T.Yokoyama
			// TMP_R_TANPINTEN_TORIATUKAI に対する処理は MB450102_TMPTanpintenToriatukaiCreate へ移動


			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENBETU_SIIRESAKIのデータ処理を開始します。↓");
			table_fm = "R_TENBETU_SIIRESAKI";
			table_to = "TMP_R_TENBETU_SIIRESAKI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TENBETU_SIIRESAKIのデータ処理を終了します。↑");
			
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENGROUPのデータ処理を開始します。↓");
			table_fm = "R_TENGROUP";
			table_to = "TMP_R_TENGROUP";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TENGROUPのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENGROUPNOのデータ処理を開始します。↓");
			table_fm = "R_TENGROUPNO";
			table_to = "TMP_R_TENGROUPNO";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TENGROUPNOのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENPOのデータ処理を開始します。↓");
			table_fm = "R_TENPO";
			table_to = "TMP_R_TENPO";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TENPOのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_R_TENSYOHIN_REIGAIのデータ処理を開始します。↓");
			table_fm = "R_TENSYOHIN_REIGAI";
			table_to = "TMP_R_TENSYOHIN_REIGAI";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_R_TENSYOHIN_REIGAIのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_SYS_GAMEN_NAMEのデータ処理を開始します。↓");
			table_fm = "SYS_GAMEN_NAME";
			table_to = "TMP_SYS_GAMEN_NAME";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_SYS_GAMEN_NAMEのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_SYSTEM_CONTROLのデータ処理を開始します。↓");
			table_fm = "SYSTEM_CONTROL";
			table_to = "TMP_SYSTEM_CONTROL";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_SYSTEM_CONTROLのデータ処理を終了します。↑");	
			
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↓TMP_INT_TANPIN_MENTENANCEのデータ処理を開始します。↓");
			table_fm = "INT_TANPIN_MENTENANCE";
			table_to = "TMP_INT_TANPIN_MENTENANCE";
			// コピー先テーブルのレコードを全件削除する
			//2006.08.22 fang UPDATE START
			//iRec = db.executeUpdate(SetDeleteSql(table_to));
			clrpfm(table_to);
			//2006.08.22 fang UPDATE END
			// コピー元テーブルの全レコードをコピー先テーブルにそれぞれコピーする
			//iRec = db.executeUpdate(SetCopySql(table_fm, table_to));
			
			cpyf(table_fm,table_to);
			db.commit();
			//batchLog.getLog().info(BATCH_ID, BATCH_NA, iRec+"件のレコードをコピーしました。");
			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑TMP_INT_TANPIN_MENTENANCEのデータ処理を終了します。↑");

			batchLog.getLog().info(BATCH_ID, BATCH_NA,"↑↑↑ TMP_R_各マスタ(全件コピー)のデータ処理を終了します。↑↑↑");
			//				20060822 shuuパッチステータス処理追加Start
			bsManager.statusRegisterProcEnd(batchID, BatchStatusManager.END_STATUS_NORMAL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			//				20060822 shuuパッチステータス処理追加End
			
		// SQLエラーが発生した場合の処理
		} catch (SQLException se) {
			db.rollback();
			//				20060822 shuuパッチステータス処理追加Start			
			bsManager.statusRegisterProcEnd(batchID, BatchStatusManager.END_STATUS_ABNORMAL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			//				20060822 shuuパッチステータス処理追加End
			batchLog.getLog().fatal(BATCH_ID, BATCH_NA, se.toString());
			throw se;

		// その他のエラーが発生した場合の処理
		} catch (Exception e) {
			db.rollback();
			//				20060822 shuuパッチステータス処理追加Start			
			bsManager.statusRegisterProcEnd(batchID, BatchStatusManager.END_STATUS_ABNORMAL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			//				20060822 shuuパッチステータス処理追加End
			batchLog.getLog().error(BATCH_ID, BATCH_NA, e.toString());
			throw e;

		// SQL終了処理
		} finally {
			db.close();
		}
		
	}
	
	/**
	 * テーブルのレコードを全件削除する。
	 * @param Tablename テーブル
	 * @return SQL文
	 */
	private String SetDeleteSql(String Tablename) {
		
		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" DELETE FROM ");
		strSql.append(  	Tablename );
		
		return strSql.toString();
	}

	/**
	 * R_xxxをTMP_R_xxxにコピー用SQL文を作る。
	 * @param Table_fm テーブル
	 * @param Table_to テーブル
	 * @return SQL文
	 */
	private String SetCopySql(String Table_fm, String Table_to) {
		
		// SQL文生成用
		StringBuffer strSql = new StringBuffer();
		strSql.append(" INSERT INTO ");
		strSql.append(    Table_to );
		strSql.append(" SELECT *");
		strSql.append(" FROM ");
		strSql.append(    Table_fm);
		
		return strSql.toString();
	}

//     ↑↑2006.07.24 fanglh カスタマイズ修正↑↑
	/**
	 * 指定されたテーブルをクリアする。
	 * AS400のCLRPFMコマンドをストアドプロシージャから間接的に呼び出すので、
	 * テーブルは短縮名で指定する必要がある。
	 * 
	 * @param shortTableName クリア対象のテーブル(短縮名)
	 * @throws SQLException
	 */
	private void clrpfm(String TableName) throws SQLException {
		
		//スキーマの取得
		String SKMA = MDWareDBUtility.getSchemaName().toUpperCase();
		

		
		StringBuffer strSql = new StringBuffer();
		/*********************************************
		 * INTプライスシールのデータをすべて削除する
		 *********************************************/
		//INTプライスシールGROのデータを削除する
		strSql.append(" SELECT DISTINCT   ");
		strSql.append(" SYSTEM_TABLE_NAME ");
		strSql.append(" FROM  ");
		strSql.append("     QSYS2.SYSCOLUMNS t1 ");
		strSql.append(" WHERE  ");
		strSql.append("     t1.TABLE_NAME = '" + TableName + "' ");
		strSql.append("   AND ");
		strSql.append("     t1.SYSTEM_TABLE_SCHEMA = '" + SKMA + "'  ");
			
		ResultSet rs = db.executeQuery(strSql.toString());
		if (rs.next()){
			
			//テーブルのLengthを取得する
			//String TableLength = StringUtility.getFormatedString(rs.getString("SYSTEM_TABLE_NAME").length(), "0000000000.00000");
			
			//クリア対象のデータを削除するSQL文。
			String SQLDELETE_TAGU = "CALL QSYS.QCMDEXC ('CLRPFM "+rs.getString("SYSTEM_TABLE_NAME") +"' ,0000000017.00000)";
			
			//クリア対象のデータを削除する。
			db.execute(SQLDELETE_TAGU);
		}
		rs.close();
		db.commit();

	}
	
	/**
	 * 指定されたテーブルをコピーする。
	 * AS400のCLRPFMコマンドをストアドプロシージャから間接的に呼び出すので、
	 * テーブルは短縮名で指定する必要がある。
	 * 
	 * @param 
	 * @throws SQLException
	 */
	private void cpyf(String TableName1,String TableName2) throws SQLException {
		
		//スキーマの取得
		String SKMA = MDWareDBUtility.getSchemaName().toUpperCase();
		

		
		StringBuffer strSql = new StringBuffer();
		/*********************************************
		 * データをコピーする
		 *********************************************/

		strSql.append(" SELECT DISTINCT   ");
		strSql.append(" SYSTEM_TABLE_NAME ");
		strSql.append(" FROM  ");
		strSql.append("     QSYS2.SYSCOLUMNS t1 ");
		strSql.append(" WHERE  ");
		strSql.append("     t1.TABLE_NAME = '" + TableName1 + "' ");
		strSql.append("   AND ");
		strSql.append("     t1.SYSTEM_TABLE_SCHEMA = '" + SKMA + "'  ");
			
		ResultSet rsfr = db.executeQuery(strSql.toString());
		
		strSql.delete(0,strSql.length());
		
		strSql.append(" SELECT DISTINCT   ");
		strSql.append(" SYSTEM_TABLE_NAME ");
		strSql.append(" FROM  ");
		strSql.append("     QSYS2.SYSCOLUMNS t1 ");
		strSql.append(" WHERE  ");
		strSql.append("     t1.TABLE_NAME = '" + TableName2 + "' ");
		strSql.append("   AND ");
		strSql.append("     t1.SYSTEM_TABLE_SCHEMA = '" + SKMA + "'  ");
			
		ResultSet rsto = db.executeQuery(strSql.toString());

		
		if (rsfr.next() && rsto.next()){
						
			//対象のデータをコピーするSQL文。
			String SQL_COPY = "CALL QSYS.QCMDEXC ('CPYF "+rsfr.getString("SYSTEM_TABLE_NAME") + " "+
								   rsto.getString("SYSTEM_TABLE_NAME") + " MBROPT(*ADD) FROMRCD(1)' ,0000000050.00000)";
			
			//データをコピーする。
			db.execute(SQL_COPY);
		}
		rsfr.close();
		rsto.close();
		db.commit();

	}
	
}
