OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_DPT_SEISAN_URI
TRAILING NULLCOLS
(
    COMP_CD                  CONSTANT "0000",
    KEIJO_DT                 POSITION(5:12) CHAR,
    TENPO_CD                 POSITION(1:4) CHAR,
    JIGYOBU_CD               POSITION(13:14) CHAR,
    GYOSYU_CD                POSITION(15:16) CHAR,
    BUNRUI1_CD               POSITION(17:20) CHAR "decode(:BUNRUI1_CD,'9999','9999',SUBSTR(:BUNRUI1_CD, 3, 4))",
    TIME                     POSITION(21:24) CHAR,
    TENANT_KB                POSITION(25:25) CHAR,
    TENANT_CD                POSITION(26:31) CHAR,
    SYOHIN_KB                POSITION(32:33) CHAR,
    URIAGE_KOMI_VL           POSITION(34:44) "decode(:URIAGE_KOMI_VL,' ',null,'　',null,to_number(:URIAGE_KOMI_VL))",
    URIAGE_GENKA_VL          POSITION(45:58) "decode(:URIAGE_GENKA_VL,' ',null,'　',null,to_number(:URIAGE_GENKA_VL))",
    URIAGE_QT                POSITION(59:64) "decode(:URIAGE_QT,' ',null,'　',null,to_number(:URIAGE_QT))",
    NEBIKI_VL                POSITION(65:75) "decode(:NEBIKI_VL,' ',null,'　',null,to_number(:NEBIKI_VL))",
    NEBIKI_QT                POSITION(76:81) "decode(:NEBIKI_QT,' ',null,'　',null,to_number(:NEBIKI_QT))",
    KAKO_VL                  POSITION(82:92) "decode(:KAKO_VL,' ',null,'　',null,to_number(:KAKO_VL))",
    KAKO_QT                  POSITION(93:98) "decode(:KAKO_QT,' ',null,'　',null,to_number(:KAKO_QT))",
    HAIKI_VL                 POSITION(99:109) "decode(:HAIKI_VL,' ',null,'　',null,to_number(:HAIKI_VL))",
    HAIKI_QT                 POSITION(110:115) "decode(:HAIKI_QT,' ',null,'　',null,to_number(:HAIKI_QT))",
    HENPIN_VL                POSITION(116:126) "decode(:HENPIN_VL,' ',null,'　',null,to_number(:HENPIN_VL))",
    HENPIN_QT                POSITION(127:132) "decode(:HENPIN_QT,' ',null,'　',null,to_number(:HENPIN_QT))",
    BUNRUI1_KYAKU_QT         POSITION(133:138) "decode(:BUNRUI1_KYAKU_QT,' ',null,'　',null,to_number(:BUNRUI1_KYAKU_QT))"
)