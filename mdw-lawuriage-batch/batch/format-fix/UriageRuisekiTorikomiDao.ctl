OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_URIAGE_RUISEKI
TRAILING NULLCOLS
(
    COMP_CD                        CONSTANT "0000",
    KEIJO_DT                       POSITION(1:8) CHAR,
    TENPO_CD                       POSITION(9:14) CHAR,
    POS_CD                         POSITION(15:27) CHAR,
    SYOHIN_CD                      POSITION(28:40) "decode(substr(:SYOHIN_CD,1,2),'02',substr(:SYOHIN_CD,1,12) || '0','23',substr(:SYOHIN_CD,1,12) || '0',:SYOHIN_CD)",
    BAITANKA_VL                    POSITION(41:51) "decode(:BAITANKA_VL,' ',null,'　',null,to_number(:BAITANKA_VL))",
    DAIBUNRUI2_CD                  POSITION(52:55) CHAR,
    RECEIPT_NA                     POSITION(56:91) CHAR,
    DAIBUNRUI1_CD                  POSITION(92:95) CHAR,
    KIKAKU_CD                      POSITION(96:104) CHAR,
    SYOHIN_KB                      POSITION(105:106) CHAR,
    MIX_MATCH_CD                   POSITION(107:110) CHAR,
    MIX_MATCH_TYPE_TX              POSITION(111:116) CHAR,
    BUNDLE_1_QT                    POSITION(117:118) CHAR,
    BUNDLE_1_VL                    POSITION(119:137) CHAR,
    BUNDLE_2_QT                    POSITION(138:139) CHAR,
    BUNDLE_2_VL                    POSITION(140:158) CHAR,
    MIX_MATCH_KANA_NA              POSITION(159:212) CHAR,
    URIAGE_VL                      POSITION(213:229) "decode(:URIAGE_VL,' ',null,'　',null,to_number(:URIAGE_VL))",
    URIAGE_GENKA_VL                POSITION(230:246) "decode(:URIAGE_GENKA_VL,' ',null,'　',null,to_number(:URIAGE_GENKA_VL))",
    URIAGE_QT                      POSITION(247:252) "decode(:URIAGE_QT,' ',null,'　',null,to_number(:URIAGE_QT))",
    NEBIKI_VL                      POSITION(253:269) "decode(:NEBIKI_VL,' ',null,'　',null,to_number(:NEBIKI_VL))",
    NEBIKI_QT                      POSITION(270:275) "decode(:NEBIKI_QT,' ',null,'　',null,to_number(:NEBIKI_QT))",
    KAKO_VL                        POSITION(276:292) "decode(:KAKO_VL,' ',null,'　',null,to_number(:KAKO_VL))",
    KAKO_QT                        POSITION(293:298) "decode(:KAKO_QT,' ',null,'　',null,to_number(:KAKO_QT))",
    HAIKI_VL                       POSITION(299:315) "decode(:HAIKI_VL,' ',null,'　',null,to_number(:HAIKI_VL))",
    HAIKI_QT                       POSITION(316:321) "decode(:HAIKI_QT,' ',null,'　',null,to_number(:HAIKI_QT))",
    HENPIN_VL                      POSITION(322:338) "decode(:HENPIN_VL,' ',null,'　',null,to_number(:HENPIN_VL))",
    HENPIN_QT                      POSITION(339:344) "decode(:HENPIN_QT,' ',null,'　',null,to_number(:HENPIN_QT))",
    KYAKU_QT                       POSITION(345:350) "decode(:KYAKU_QT,' ',null,'　',null,to_number(:KYAKU_QT))",
    BUNRUI1_CD                     POSITION(353:356) CHAR,
    BUNRUI2_CD                     POSITION(357:361) CHAR,
    BUNRUI5_CD                     POSITION(362:367) CHAR,
    END_HANBAI_TS                  POSITION(368:373) CHAR,
    MM_POINT_TAISYO_KB             POSITION(374:374) CHAR
)