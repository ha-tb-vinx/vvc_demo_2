OPTIONS(LOAD=-1,ROWS=-1,ERRORS=0,DIRECT=y)
LOAD DATA
CHARACTERSET UTF8
TRUNCATE
INTO TABLE WK_TEN_INSHIZEI
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED '"'
TRAILING NULLCOLS
(
    COMP_CD                  CHAR,
    KEIJO_DT                 CHAR,
    TENPO_CD                 CHAR,
    POS_QT               	 INTEGER EXTERNAL,
    SEISAN_QT                EXPRESSION ":POS_QT",
    INSERT_USER_ID           CHAR,
    INSERT_TS                CHAR,
    UPDATE_USER_ID           CHAR,
    UPDATE_TS                CHAR
)