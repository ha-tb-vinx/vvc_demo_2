OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_KAIKEI_SEISAN
TRAILING NULLCOLS
(
	COMP_CD                  CONSTANT "0000",
	TENPO_CD                 POSITION(1:4) CHAR,
	KEIJO_DT      			 POSITION(5:12) CHAR,
	POS_NB      			 POSITION(13:16) CHAR,
	DAY_GROSS_VL        	 POSITION(101:111) CHAR,
	DAY_NET_VL				 POSITION(112:122) "decode(:DAY_NET_VL,' ',null,'　',null,to_number(:DAY_NET_VL))",	
	KEI_1_VL				 POSITION(557:567) "decode(:KEI_1_VL,' ',null,'　',null,to_number(:KEI_1_VL))",
	KEI_1_COUNT_QT			 POSITION(551:556) "decode(:KEI_1_COUNT_QT,' ',null,'　',null,to_number(:KEI_1_COUNT_QT))",
	KEI_2_VL				 POSITION(574:584) "decode(:KEI_2_VL,' ',null,'　',null,to_number(:KEI_2_VL))",
	KEI_2_COUNT_QT			 POSITION(568:573) "decode(:KEI_2_COUNT_QT,' ',null,'　',null,to_number(:KEI_2_COUNT_QT))",
	KEI_3_VL				 POSITION(591:601) "decode(:KEI_3_VL,' ',null,'　',null,to_number(:KEI_3_VL))",
	KEI_3_COUNT_QT			 POSITION(585:590) "decode(:KEI_3_COUNT_QT,' ',null,'　',null,to_number(:KEI_3_COUNT_QT))",
	KEI_4_VL				 POSITION(608:618) "decode(:KEI_4_VL,' ',null,'　',null,to_number(:KEI_4_VL))",
	KEI_4_COUNT_QT			 POSITION(602:607) "decode(:KEI_4_COUNT_QT,' ',null,'　',null,to_number(:KEI_4_COUNT_QT))",
	KEI_5_VL				 POSITION(625:635) "decode(:KEI_5_VL,' ',null,'　',null,to_number(:KEI_5_VL))",
	KEI_5_COUNT_QT			 POSITION(619:624) "decode(:KEI_5_COUNT_QT,' ',null,'　',null,to_number(:KEI_5_COUNT_QT))",
	KEI_6_VL				 POSITION(642:652) "decode(:KEI_6_VL,' ',null,'　',null,to_number(:KEI_6_VL))",
	KEI_6_COUNT_QT			 POSITION(636:641) "decode(:KEI_6_COUNT_QT,' ',null,'　',null,to_number(:KEI_6_COUNT_QT))",
	KEI_7_VL				 POSITION(659:669) "decode(:KEI_7_VL,' ',null,'　',null,to_number(:KEI_7_VL))",
	KEI_7_COUNT_QT			 POSITION(653:658) "decode(:KEI_7_COUNT_QT,' ',null,'　',null,to_number(:KEI_7_COUNT_QT))",
	KEI_8_VL				 POSITION(676:686) "decode(:KEI_8_VL,' ',null,'　',null,to_number(:KEI_8_VL))",
	KEI_8_COUNT_QT			 POSITION(670:675) "decode(:KEI_8_COUNT_QT,' ',null,'　',null,to_number(:KEI_8_COUNT_QT))",
	KEI_9_VL				 POSITION(693:703) "decode(:KEI_9_VL,' ',null,'　',null,to_number(:KEI_9_VL))",
	KEI_9_COUNT_QT			 POSITION(687:692) "decode(:KEI_9_COUNT_QT,' ',null,'　',null,to_number(:KEI_9_COUNT_QT))",
	KEI_10_VL				 POSITION(710:720) "decode(:KEI_10_VL,' ',null,'　',null,to_number(:KEI_10_VL))",
	KEI_10_COUNT_QT			 POSITION(704:709) "decode(:KEI_10_COUNT_QT,' ',null,'　',null,to_number(:KEI_10_COUNT_QT))",
	KYAKU_QT				 POSITION(822:827) "decode(:KYAKU_QT,' ',null,'　',null,to_number(:KYAKU_QT))",
	SOTOZEI_TAISYO_VL		 POSITION(987:997) "decode(:SOTOZEI_TAISYO_VL,' ',null,'　',null,to_number(:SOTOZEI_TAISYO_VL))",
	UCHIZEI_TAISYO_VL		 POSITION(998:1008) "decode(:UCHIZEI_TAISYO_VL,' ',null,'　',null,to_number(:UCHIZEI_TAISYO_VL))",
	HIKAZEI_TAISYO_VL		 POSITION(1009:1019) "decode(:HIKAZEI_TAISYO_VL,' ',null,'　',null,to_number(:HIKAZEI_TAISYO_VL))",
	SOTOZEI_VL				 POSITION(1020:1030) "decode(:SOTOZEI_VL,' ',null,'　',null,to_number(:SOTOZEI_VL))",
	UCHIZEI_VL				 POSITION(1031:1041) "decode(:UCHIZEI_VL,' ',null,'　',null,to_number(:UCHIZEI_VL))"
)	