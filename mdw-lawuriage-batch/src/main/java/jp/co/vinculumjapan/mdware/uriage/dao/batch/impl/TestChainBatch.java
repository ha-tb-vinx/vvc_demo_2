package jp.co.vinculumjapan.mdware.uriage.dao.batch.impl;

import java.util.ArrayList;
import java.util.List;

import jp.co.vinculumjapan.swc.commons.dao.BatchController;

public class TestChainBatch {

    public static void main(String[] args) {
        
        BatchController bc;
        List<String> batchList = nightBatchList();
//      List<String> batchList = dailyBatchList();
        
        for(int i= 0; i < batchList.size(); i++) {
            String[] batch = new String[] { batchList.get(i) };
            bc = new  BatchController(batch);
            bc.executeBatch("jp.co.vinculumjapan.swc.commons.dao.BatchDaoInvoker");;
        }

    }
    
    private static List<String> dailyBatchList() {
        List<String> ret = new ArrayList<String>();
        
//        ret.add("URIB013420");
//        ret.add("URIB013410");
//        ret.add("URIB013010");
//        ret.add("URIB013020");
        ret.add("URIB013330");
        ret.add("URIB013030");
        ret.add("URIB013110");
        ret.add("URIB013120");
        ret.add("URIB013210");
        ret.add("URIB013220");
        ret.add("URIB013230");
        ret.add("URIB013310");
        ret.add("URIB013320");
        ret.add("URIB013330");
        ret.add("URIB013340");
        ret.add("URIB013350");
        ret.add("URIB013360");
        ret.add("URIB014010");
        ret.add("URIB014020");
        ret.add("URIB014040");
        ret.add("URIB014050");
        ret.add("URIB014060");
        ret.add("URIB01407020");
        ret.add("URIB014080");
        
        return ret;
    }
    

    private static List<String> nightBatchList() {
        List<String> ret = new ArrayList<String>();

//        ret.add("URIB013020");
        
//        ret.add("URIB011210"); // Eレコード営業日付加　処理を開始します。
//        ret.add("URIB012050"); // POSジャーナル取込　処理を開始します。
//        ret.add("URIB012740"); // Aレコード取込前処理　処理を開始します。
//        ret.add("URIB012750"); // Fレコード取込前処理　処理を開始します。
//        ret.add("URIB012820"); // Hレコード取込前処理　処理を開始します。
//        ret.add("URIB012510"); // 営業日付加　処理を開始します。
//        ret.add("URIB012590"); // 営業日付加C　処理を開始します。
//        ret.add("URIB012600"); // 営業日付加D　処理を開始します。
//        ret.add("URIB012640"); // 営業日付加K　処理を開始します。
//        ret.add("URIB012780"); // 営業日付加H　処理を開始します。
//        ret.add("URIB012970"); // 旧商品コード読替（Aレコード）　処理を開始します。
//        ret.add("URIB012670"); // マスタチェック（Aレコード）　処理を開始します。
        ret.add("URIB012700"); // データ振分（Aレコード）　処理を開始します。
        ret.add("URIB012680"); // マスタチェック（Bレコード）　処理を開始します。
        ret.add("URIB012710"); // データ振分（Bレコード）　処理を開始します。
        ret.add("URIB012760"); // マスタチェック（Cレコード）　処理を開始します。
        ret.add("URIB012770"); // データ振分（Cレコード）　処理を開始します。
        ret.add("URIB012690"); // マスタチェック（Fレコード）　処理を開始します。
        ret.add("URIB012720"); // データ振分（Fレコード）　処理を開始します。
        ret.add("URIB012870"); // マスタチェック（Kレコード）　処理を開始します。
        ret.add("URIB012880"); // データ振分（Kレコード）　処理を開始します。
        ret.add("URIB012900"); // マスタチェック（Dレコード）　処理を開始します。
        ret.add("URIB012910"); // データ振分（Dレコード）　処理を開始します。
        ret.add("URIB012920"); // マスタチェック（Eレコード）　処理を開始します。
        ret.add("URIB012930"); // データ振分（Eレコード）　処理を開始します。
        ret.add("URIB012940"); // マスタチェック（Hレコード）　処理を開始します。
        ret.add("URIB012950"); // データ振分（Hレコード）　処理を開始します。
        ret.add("URIB021010"); // 精算状況データ作成処理　処理を開始します。
        ret.add("URIB012010"); // POS実績取込処理(単品精算)　処理を開始します。
//        ret.add("URIB012110"); // 売上外入金除外処理(単品精算)　処理を開始します。
//        ret.add("URIB012210"); // マスタ情報取得処理(単品精算)　処理を開始します。
//        ret.add("URIB012320"); // 精算データ作成処理　処理を開始します。
//        ret.add("URIB501040"); // 卸伝票抽出処理　処理を開始します。
//        ret.add("URIB501010"); // 単品精算データ作成処理　処理を開始します。
//        ret.add("URIB012520"); // ハンパー商品ばらし　処理を開始します。
//        ret.add("URIB012530"); // ハンパー商品集約　処理を開始します。
//        ret.add("URIB012020"); // POS実績取込処理(部門精算)　処理を開始します。
//        ret.add("URIB012120"); // 売上外入金除外処理(部門精算)　処理を開始します。
//        ret.add("URIB012840"); // 日別客数集計Arecデータ作成　処理を開始します。
//        ret.add("URIB012660"); // 部門精算客数付加　処理を開始します。
//        ret.add("URIB012220"); // マスタ情報取得処理(部門精算)　処理を開始します。
//        ret.add("URIB012330"); // 精算データ作成処理(部門精算)　処理を開始します。
//        ret.add("URIB031010"); // DPT精算売上データ取込処理　処理を開始します。
//        ret.add("URIB031020"); // 店別DPT売上集計処理　処理を開始します。
//        ret.add("URIB031025"); // IF DPT精算売上（仕入用）ワーク作成処理　処理を開始します。
//        ret.add("URIB031030"); // 客数実績データ作成処理　処理を開始します。
//        ret.add("URIB031040"); // FTPデータ転送処理　処理を開始します。
//        ret.add("URIB012030"); // POS実績取込処理(レジ別取引精算)　処理を開始します。
//        ret.add("URIB012540"); // レジ別取引精算貸方レコード作成処理(レジ取引精算)　処理を開始します。
//        ret.add("URIB012230"); // マスタ情報取得処理(レジ別取引精算)　処理を開始します。
//        ret.add("URIB012340"); // 精算データ作成処理（レジ別取引精算）　処理を開始します。
//        ret.add("URIB501030"); // レジ別取引精算データ作成処理　処理を開始します。
//        ret.add("URIB012401"); // POS実績取込処理（レジ別取引精算Erec）　処理を開始します。
//        ret.add("URIB012402"); // マスタ情報取得処理（レジ別取引精算Erec）　処理を開始します。
//        ret.add("URIB012403"); // 精算データ作成処理（レジ別取引精算Erec）　処理を開始します。
//        ret.add("URIB012040"); // POS実績取込処理(責任者精算)　処理を開始します。
//        ret.add("URIB012240"); // マスタ情報取得処理(責任者精算)　処理を開始します。
//        ret.add("URIB012350"); // 精算データ作成処理(責任者精算)　処理を開始します。
//        ret.add("URIB012790"); // POS実績取込処理(責任者精算Crec)　処理を開始します。
//        ret.add("URIB012800"); // マスタ情報取得処理(責任者精算Crec)　処理を開始します。
//        ret.add("URIB012810"); // 精算データ作成処理(責任者精算Crec)　処理を開始します。
//        ret.add("URIB012550"); // POS実績取込処理(レシート別取引精算)　処理を開始します。
//        ret.add("URIB012560"); // マスタ情報取得処理(レシート別取引精算)　処理を開始します。
//        ret.add("URIB012570"); // 精算データ作成処理(レシート別取引精算)　処理を開始します。
//        ret.add("URIB012580"); // 店別レシート別精算データ作成　処理を開始します。
//        ret.add("URIB012310"); // アラーム処理　処理を開始します。
//        ret.add("URIB041010"); // 会計精算売上データ取込処理　処理を開始します。
//        ret.add("URIB041040"); // 会計精算売上データ集計処理　処理を開始します。
//        ret.add("URIB041020"); // 店別精算情報集計処理　処理を開始します。
//        ret.add("URIB041030"); // 店別印紙税対象データ作成処理　処理を開始します。
//        ret.add("URIB131020"); // DPT売上累積データ抽出処理　処理を開始します。
//        ret.add("URIB131030"); // DPT別売上累積データ集計処理　処理を開始します。
//        ret.add("URIB131035"); // FTPデータ転送処理　処理を開始します。
//        ret.add("URIB051010"); // チェッカー別売上取込処理　処理を開始します。
//        ret.add("URIB051030"); // チェッカー別売上Crec取込処理　処理を開始します。
//        ret.add("URIB051020"); // チェッカー別売上集計処理　処理を開始します。
//        ret.add("URIB081010"); // 単品売上取込処理　処理を開始します。
//        ret.add("URIB081020"); // 単品売上データ集計処理　処理を開始します。
//        ret.add("URIB081030"); // 単品売上IF用データ作成処理　処理を開始します。
//        ret.add("URIB081040"); // IF新単品サマリファイル作成処理　処理を開始します。
//        ret.add("URIB011021S"); // オンメモリサーバー転送処理(日次商品)　処理を開始します。
//        ret.add("URIB062010"); // 商品別レジ値引データ作成処理　処理を開始します。
//        ret.add("URIB062020"); // 部門別レジ値引データ作成処理　処理を開始します。
//        ret.add("URIB062030"); // 商品別MM値引データ作成処理　処理を開始します。
//        ret.add("URIB062040"); // 商品別マスタ差額データ作成処理　処理を開始します。
//        ret.add("URIB062050"); // POS値上下データ作成処理　処理を開始します。
//        ret.add("URIB062060"); // 見切実績データ作成処理　処理を開始します。
//        ret.add("URIB062070"); // 店別DPT打ち売上ワーク作成処理　処理を開始します。
//        ret.add("URIB062080"); // 店別DPT打ち売上データ作成処理　処理を開始します。
//        ret.add("URIB221010"); // 売上累積取込処理　処理を開始します。
//        ret.add("URIB221020"); // 売上累積データ集計処理　処理を開始します。
//        ret.add("URIB241010"); // 売上累積データ移送処理　処理を開始します。
//        ret.add("URIB224020"); // 売上累積差分データ移送処理　処理を開始します。
//        ret.add("URIB251010"); // 前日売上累積データ移送処理　処理を開始します。
//        ret.add("URIB151010"); // データ退役処理　処理を開始します。
//        ret.add("URIB191010"); // テーブル再編成処理　処理を開始します。
//        ret.add("URIB012850"); // Ａレコード返品情報カットデータ作成　処理を開始します。
//        ret.add("URIB012130"); // 発行済VATINVOICE情報累積　処理を開始します。
//        ret.add("URIB501020"); // 売上INVOICE作成処理　処理を開始します。
//        ret.add("URIB012730"); // INVOICE発行済情報付与　処理を開始します。
//        ret.add("URIB012406"); // Ｆレコード履歴カットデータ作成　処理を開始します。
//        ret.add("URIB012407"); // INVOICE発行／取消情報更新　処理を開始します。
//        ret.add("URIB012830"); // VATスキップ履歴作成（Fレコード）　処理を開始します。
//        ret.add("URIB012860"); // 返品レシート集計データ作成　処理を開始します。
//        ret.add("URIB012890"); // 店別レシート別値引データ作成　処理を開始します。
//        ret.add("URIB012404"); // レシート取消集計データ作成　処理を開始します。
//        ret.add("URIB012405"); // 店別レシート別データ作成　処理を開始します。
//        ret.add("URIB291010"); // ハンパー構成商品売上実績データ作成　処理を開始します。
//        ret.add("URIB301010"); // ハンパー売上実績データ作成　処理を開始します。
//        ret.add("URIB291020"); // ハンパー構成商品売上実績データ作成（卸）　処理を開始します。
//        ret.add("URIB301020"); // ハンパー売上実績データ作成（卸）　処理を開始します。
        
        return ret;
    }
    
}
