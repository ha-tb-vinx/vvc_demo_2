package jp.co.vinculumjapan.mdware.uriage.util;

import java.math.BigDecimal;

public class TorikomiUtility {

    /**
     * YYYYMMDDからスラッシュ"/"付きの日付に変換して営業日とする
     * 
     * @param fileName
     * @return
     */
    public static String createEigyobi(String Date) {

        StringBuilder ret = new StringBuilder();

        // 年のみの取得
        ret.append(Date.substring(0, 4));
        ret.append("/");
        // 月取得
        ret.append(Date.substring(4, 6));
        ret.append("/");
        // 日取得
        ret.append(Date.substring(6, 8));
        return ret.toString();
    }

    public static String toEigyoDtForRecordCheck(String eigyoDt) {
        
        // スラッシュを除去
        return eigyoDt.replaceAll("/", "");
    }
    
    
    /**
     * ファイル名の営業日の形式へ変換
     * 
     * @param dateString 日付文字列
     * @return
     */
    public static String toEigyoDtByFileNameFormat(String dateString) {

        String year = dateString.substring(0, 4);
        String month = dateString.substring(4, 6);
        String day = dateString.substring(6, 8);
        
        return year + "/" + month + "/" + day;
    }

    /**
     * Short SKU NumberをSKU Numberへ変換
     * 左
     * 
     * @param dateString 日付文字列
     * @return
     */
    public static String getSyohinCd(String shortSkuNo) {

        String skuNo;
        int index = shortSkuNo.indexOf(' ');
        
        if (-1 == index) {
            skuNo = shortSkuNo;
        } else {
            skuNo = shortSkuNo.substring(0, index);
        }
        
        return addZeroPrefix(skuNo, 13);
    }

    /**
     * TMP_SEKININSYA_SEISANにINSERTする際の桁合わせ（前段0埋め）
     * 
     * @param value 対象文字列
     * @param digit 桁数
     * @return 編集後文字列
     */
    public static String addZeroPrefix(String value, int digit) {
        while (value.length() < digit) {
            value = "0".concat(value);
        }
        return value;
    }
    
    public static String toSignedStringNumberValue(BigDecimal value, int digit) {
        
        char sign;
        
        if (-1 == value.signum()) {
            sign = '-';
        } else {
            sign = '+';
        }
        
        return toSignedStringNumberValue(value.abs().toString(), sign, digit);
    }
    
    public static String toSignedStringNumberValue(int value, int digit) {
        
        char sign;
        
        if (0 > value) {
            sign = '-';
        } else {
            sign = '+';
        }
        
        return toSignedStringNumberValue(String.valueOf(value), sign, digit);
    }
    
    private static String toSignedStringNumberValue(String value, char sign, int digit) {
        
        return sign + addZeroPrefix(value, digit - 1);
    }
    
    public static char getRecordFormat(String record) {
        return record.charAt(24);
    }
    
    public static String getStoreCd(String record) {
        return record.substring(4, 8);
    }
    
    public static String getStoreCdForFormat1(String record) {
        return record.substring(0, 4);
    }
    
    public static String getSkuOrBarcodeNumber(String record) {
        return record.substring(30, 43);
    }
    
    public static String createTanpinCd(String record) {
        return record.substring(30, 43);
    }
    
//    public static String getTanpinCd(String record) {
//        return record.substring(30, 43);
//    }
//    
    public static String getEigyoDt(String record) {
        return record.substring(129, 137);
    }
    
    public static String getSalesType(String record) {
        return record.substring(26, 30);
    }
    
    
    /**
     * 計量器の場合、小数点が入っている場合があるので、四捨五入して単位をそろえる
     * 
     * @param record 
     * @return
     */
    public static BigDecimal getActualQuantitySoldWithRoundHalfUp(String record) {
        
        BigDecimal value = new BigDecimal(getActualQuantitySold(record));
        return value.setScale(0, BigDecimal.ROUND_HALF_UP);
    }
    
    // TODO
    private static String getActualQuantitySold(String record) {
        return record.substring(43, 52);
    }
    
    public static String getRegularExtendedRetailPrice(String record) {
        return record.substring(52, 75);
    }
    
    public static String getActualExtendedRetailPrice(String record) {
        return record.substring(75, 98);
    }
    
    public static String getMixAndMatchDiscount(String record) {
        return record.substring(212, 227);
    }
    
    public static String getPosDiscount(String record) {
        return record.substring(242, 257);
    }
    
    public static String getArticleDiscount(String record) {
        return record.substring(287, 302);
    }
    
    public static String getPromotionDiscount(String record) {
        return record.substring(317, 332);
    }
    
    public static String createTenpoCdAndTanpinCdConcatString(String record) {
        return createTenpoCd(record).concat(createTanpinCd(record));
    }
    
    public static String createTenpoCdAndCheckerCdConcatString(String record) {
        return createTenpoCd(record).concat(createCheckerCd(record));
    }
    
    public static String getTransactionNo(String record) {
        return record.substring(16, 23);
    }
    
    public static String getSalesTime(String record) {
        return record.substring(137, 141);
    }
    
    public static String getTenpoCdByKey(String key) {
        return key.substring(0, 6);
    }
    
    public static String getTanpinCdByKey(String key) {
        return key.substring(6, 19);
    }
    public static String toSaisyuUriageTm(String salesTime) {
        
        return salesTime.substring(0, 2) + ":" + salesTime.substring(2);
    }
    
    public static boolean isFormat1Record(int recordLength) {
        
        return recordLength == 81;
    }
    
    public static boolean isFormat2Record(int recordLength) {
        
        return recordLength == 28;
    }
    
    public static String getShortSkuNumber(String record) {
        
        return record.substring(4, 17);
    }
    
    public static String getJikantaiNo(String record) {
        
        String hour = record.substring(17, 21);
        
        // TODO 仮で頭2桁を使用
        return hour.substring(0, 2);
    }
    
    public static boolean isStoreCustomerCountType(String record) {
        
        System.out.println(getCustomerCountType(record));
        
        return "STR".equals(getCustomerCountType(record));
    }
    
    private static String getCustomerCountType(String record) {
        return record.substring(4, 7);
    }
    
    public static String getCustomerCount(String record) {
        
        return record.substring(21, 28);
    }
    
    public static String createCheckerCd(String record) {
        return "0".concat(record.substring(190, 197));
    }
    
    public static String createTenpoCd(String record) {
        
        return "00".concat(getStoreCd(record));
    }
    
    public static String getFormat1TenpoCd(String record) {
        
        if(record != null && record.length() >= 4) {
            return "00".concat(record.substring(0, 4));
        } else {
            return "000000";
        }
    }
}
