OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_TANPIN_MEI_SABUN
TRAILING NULLCOLS
(
    COMP_CD                        CONSTANT "0000",
    TORIHIKI_TS                    POSITION(1:14) CHAR,
    TENPO_CD                       POSITION(15:20) CHAR, 
    TERMINAL_NB                    POSITION(21:24) CHAR, 
    CHECKER_CD                     POSITION(25:33) CHAR, 
    TOROHIKI_NB                    POSITION(34:37) "decode(:TOROHIKI_NB,' ',null,'　',null,to_number(:TOROHIKI_NB))", 
    TORIHIKI_MEISAI_NB             POSITION(38:41) "decode(:TORIHIKI_MEISAI_NB,' ',null,'　',null,to_number(:TORIHIKI_MEISAI_NB))", 
    TORIKESHI_FG                   POSITION(42:42) CHAR, 
    HENPIN_FG                      POSITION(43:43) CHAR, 
    RE_ENTRY_FG                    POSITION(44:44) CHAR, 
    KINKYU_ADD_FG                  POSITION(45:45) CHAR, 
    HAIKI_FG                       POSITION(46:46) CHAR, 
    SCAN_COUNT_QT                  POSITION(47:50) "decode(:SCAN_COUNT_QT,' ',null,'　',null,to_number(:SCAN_COUNT_QT))", 
    KEYBOARD_COUNT_QT              POSITION(51:54) "decode(:KEYBOARD_COUNT_QT,' ',null,'　',null,to_number(:KEYBOARD_COUNT_QT))", 
    REPORT_CD                      POSITION(55:58) CHAR, 
    BUNRUI1_CD                     POSITION(59:62) CHAR, 
    BUNRUI5_CD                     POSITION(63:68) CHAR, 
    PLU_1_CD                       POSITION(69:81) CHAR, 
    PLU_2_CD                       POSITION(82:94) CHAR, 
    URIAGE_QT                      POSITION(95:103) "decode(:URIAGE_QT,' ',null,'　',null,to_number(:URIAGE_QT))", 
    URIAGE_KOMI_VL                 POSITION(104:120) "decode(:URIAGE_KOMI_VL,' ',null,'　',null,to_number(:URIAGE_KOMI_VL))", 
    NEBIKI_TYPE_CD                 POSITION(121:124) CHAR, 
    NEBIKI_QT                      POSITION(125:133) "decode(:NEBIKI_QT,' ',null,'　',null,to_number(:NEBIKI_QT))", 
    NEBIKI_VL                      POSITION(134:150) "decode(:NEBIKI_VL,' ',null,'　',null,to_number(:NEBIKI_VL))", 
    SET_NEBIKI_VL                  POSITION(151:164) "decode(:SET_NEBIKI_VL,' ',null,'　',null,to_number(:SET_NEBIKI_VL))", 
    URIAGE_GENKA_VL                POSITION(165:181) "decode(:URIAGE_GENKA_VL,' ',null,'　',null,to_number(:URIAGE_GENKA_VL))", 
    MIX_MATCH_CD                   POSITION(182:185) CHAR, 
    MIX_MATCH_NEBIKI_VL            POSITION(186:202) "decode(:MIX_MATCH_NEBIKI_VL,' ',null,'　',null,to_number(:MIX_MATCH_NEBIKI_VL))", 
    DECIMAL_QT                     POSITION(203:211) "decode(:DECIMAL_QT,' ',null,'　',null,to_number(:DECIMAL_QT))"
)