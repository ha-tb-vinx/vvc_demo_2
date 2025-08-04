OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_OFF_KAIKEI_SEISAN
TRAILING NULLCOLS
(
    COMP_CD                        CONSTANT "0000",
	TENPO_CD                       POSITION(1:4	) CHAR,
	ENTRY_DT                       POSITION(5:12) CHAR,
	KEIJO_DT                       POSITION(13:20) CHAR,
	POS_NB                         POSITION(21:24) CHAR,
	DAY_NET_VL                     CONSTANT "0",
	KEI_1_VL                       POSITION(25:35) "decode(:KEI_1_VL,' ',null,'　',null,to_number(:KEI_1_VL))",
	KEI_1_COUNT_QT                 POSITION(36:41) "decode(:KEI_1_COUNT_QT,' ',null,'　',null,to_number(:KEI_1_COUNT_QT))",
	KEI_2_VL                       POSITION(42:52) "decode(:KEI_2_VL,' ',null,'　',null,to_number(:KEI_2_VL))",
	KEI_2_COUNT_QT                 POSITION(53:58) "decode(:KEI_2_COUNT_QT,' ',null,'　',null,to_number(:KEI_2_COUNT_QT))",
	KEI_3_VL                       POSITION(59:69) "decode(:KEI_3_VL,' ',null,'　',null,to_number(:KEI_3_VL))",
	KEI_3_COUNT_QT                 POSITION(70:75) "decode(:KEI_3_COUNT_QT,' ',null,'　',null,to_number(:KEI_3_COUNT_QT))",
	KEI_4_VL                       POSITION(76:86) "decode(:KEI_4_VL,' ',null,'　',null,to_number(:KEI_4_VL))",
	KEI_4_COUNT_QT                 POSITION(87:92) "decode(:KEI_4_COUNT_QT,' ',null,'　',null,to_number(:KEI_4_COUNT_QT))",
	KEI_5_VL                       POSITION(93:103) "decode(:KEI_5_VL,' ',null,'　',null,to_number(:KEI_5_VL))",
	KEI_5_COUNT_QT                 POSITION(104:109) "decode(:KEI_5_COUNT_QT,' ',null,'　',null,to_number(:KEI_5_COUNT_QT))",
	KEI_6_VL                       POSITION(110:120) "decode(:KEI_6_VL,' ',null,'　',null,to_number(:KEI_6_VL))",
	KEI_6_COUNT_QT                 POSITION(121:126) "decode(:KEI_6_COUNT_QT,' ',null,'　',null,to_number(:KEI_6_COUNT_QT))",
	KEI_7_VL                       POSITION(127:137) "decode(:KEI_7_VL,' ',null,'　',null,to_number(:KEI_7_VL))",
	KEI_7_COUNT_QT                 POSITION(138:143) "decode(:KEI_7_COUNT_QT,' ',null,'　',null,to_number(:KEI_7_COUNT_QT))",
	KEI_8_VL                       POSITION(144:154) "decode(:KEI_8_VL,' ',null,'　',null,to_number(:KEI_8_VL))",
	KEI_8_COUNT_QT                 POSITION(155:160) "decode(:KEI_8_COUNT_QT,' ',null,'　',null,to_number(:KEI_8_COUNT_QT))",
	KEI_9_VL                       POSITION(161:171) "decode(:KEI_9_VL,' ',null,'　',null,to_number(:KEI_9_VL))",
	KEI_9_COUNT_QT                 POSITION(172:177) "decode(:KEI_9_COUNT_QT,' ',null,'　',null,to_number(:KEI_9_COUNT_QT))",
	KEI_10_VL                      POSITION(178:188) "decode(:KEI_10_VL,' ',null,'　',null,to_number(:KEI_10_VL))",
	KEI_10_COUNT_QT                POSITION(189:194) "decode(:KEI_10_COUNT_QT,' ',null,'　',null,to_number(:KEI_10_COUNT_QT))",
	SOTOZEI_TAISYO_VL              POSITION(195:205) "decode(:SOTOZEI_TAISYO_VL,' ',null,'　',null,to_number(:SOTOZEI_TAISYO_VL))",
	UCHIZEI_TAISYO_VL              POSITION(206:216) "decode(:UCHIZEI_TAISYO_VL,' ',null,'　',null,to_number(:UCHIZEI_TAISYO_VL))",
	HIKAZEI_TAISYO_VL              POSITION(217:227) "decode(:HIKAZEI_TAISYO_VL,' ',null,'　',null,to_number(:HIKAZEI_TAISYO_VL))",
	SOTOZEI_VL                     POSITION(228:238) "decode(:SOTOZEI_VL,' ',null,'　',null,to_number(:SOTOZEI_VL))",
	UCHIZEI_VL                     POSITION(239:249) "decode(:UCHIZEI_VL,' ',null,'　',null,to_number(:UCHIZEI_VL))",
	TENSU_QT                       POSITION(250:255) "decode(:TENSU_QT,' ',null,'　',null,to_number(:TENSU_QT))",
	KYAKU_QT                       POSITION(256:261) "decode(:KYAKU_QT,' ',null,'　',null,to_number(:KYAKU_QT))"
)